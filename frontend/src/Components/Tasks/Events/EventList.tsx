import React, { useEffect, useState } from "react";
import Event from "./Event";
import UpdateEventPopup from "./UpdateEvent";
import "../../../EventList.css";
import "../../../Event.css";


interface EventProps {
  id: number;
  name: string;
  description?: string;
  startTime: string;
  endTime: string;
  location: string;
  maxParticipants: number;
  currentParticipants: number;
  applied?: boolean;
}

interface Applicant {
  name: string;
  email: string;
}

interface EventListProps {
  isAdmin: boolean;
}

const EventList: React.FC<EventListProps> = ({ isAdmin }) => {
  const [events, setEvents] = useState<EventProps[]>([]);
  const [selectedEvent, setSelectedEvent] = useState<EventProps | null>(null);
  const [isUpdatePopupVisible, setIsUpdatePopupVisible] = useState(false);
  const [applicants, setApplicants] = useState<Applicant[]>([]);
  const [showApplicantsPopup, setShowApplicantsPopup] = useState(false);

  const fetchEvents = async () => {
    try {
      const response = await fetch("http://localhost:8080/events");
      if (!response.ok) throw new Error("Failed to fetch events");
      const data: EventProps[] = await response.json();
      setEvents(data);
    } catch (err) {
      console.error("Error fetching events:", err);
    }
  };

  const deleteEvent = async (id: number) => {
    const userId = localStorage.getItem("userId");
    try {
      const response = await fetch(`http://localhost:8080/events/${id}?userId=${userId}`, {
        method: "DELETE",
      });
      if (!response.ok) throw new Error("Failed to delete event.");
      setEvents(events.filter((event) => event.id !== id));
    } catch (err) {
      console.error("Error deleting event:", err);
      alert("Failed to delete event.");
    }
  };

  const applyToEvent = async (eventId: number) => {
    const userId = localStorage.getItem("userId");
    try {
      const response = await fetch(`http://localhost:8080/events/${eventId}/apply?userId=${userId}`, {
        method: "POST",
      });
      if (!response.ok) throw new Error("Failed to apply to event");
      fetchEvents(); // Refresh the events after applying
    } catch (err) {
      console.error("Error applying to event:", err);
      alert("Failed to apply to the event.");
    }
  };


  const updateEvent = async (updatedEvent: EventProps) => {
    const userId = localStorage.getItem("userId");
    if (!userId) {
      alert("User ID not found. Please log in.");
      return;
    }

    try {
      const response = await fetch(`http://localhost:8080/events/${updatedEvent.id}?userId=${userId}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(updatedEvent),
      });

      if (!response.ok) throw new Error("Failed to update event");

      const updatedEventResponse = await response.json(); // Get the updated event from the backend
      setEvents((prev) =>
          prev.map((event) => (event.id === updatedEvent.id ? updatedEventResponse : event))
      );
      alert("Event updated successfully.");
    } catch (err) {
      console.error("Error updating event:", err);
      alert("Failed to update event.");
    }
  };


  const viewApplicants = async (eventId: number) => {
    const userId = localStorage.getItem("userId");
    try {
      const response = await fetch(`http://localhost:8080/events/${eventId}/participants?userId=${userId}`);
      if (!response.ok) throw new Error("Failed to fetch applicants");
      const participants = await response.json();
      setApplicants(participants);
      setShowApplicantsPopup(true);
    } catch (err) {
      console.error("Error fetching applicants:", err);
      alert("Failed to fetch applicants.");
    }
  };

  useEffect(() => {
    fetchEvents();
  }, []);

  return (
      <div className="event-list-container">
        <div className="event-list">
          {events.map((event) => (
              <Event
                  key={event.id}
                  event={event}
                  isAdmin={isAdmin}
                  onDelete={deleteEvent}
                  onApply={applyToEvent}
                  onUpdate={(event) => {
                    setSelectedEvent(event);
                    setIsUpdatePopupVisible(true);
                  }}
                  onViewApplicants={viewApplicants}
              />
          ))}
        </div>
        {isAdmin && selectedEvent && (
            <UpdateEventPopup
                isVisible={isUpdatePopupVisible}
                onClose={() => setIsUpdatePopupVisible(false)}
                event={selectedEvent}
                onUpdate={updateEvent}
            />
        )}
        {showApplicantsPopup && (
            <div className="applicants-popup">
              <h2>Applicants</h2>
              <ul>
                {applicants.map((applicant, index) => (
                    <li key={index}>
                      <strong>Name:</strong> {applicant.name}<br />
                      <strong>Email:</strong> {applicant.email}<br />
                    </li>
                ))}
              </ul>
              <button onClick={() => setShowApplicantsPopup(false)}>Close</button>
            </div>
        )}
      </div>
  );
};

export default EventList;
