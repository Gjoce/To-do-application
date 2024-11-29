import React, { useEffect, useState } from "react";
import Event from "./Event";
import UpdateEventPopup from "./UpdateEvent";
import "../../../EventList.css";
import { useNavigate } from "react-router-dom";

interface EventProps {
  id: number;
  name: string;
  description?: string;
  startTime: string;
  endTime: string;
  location: string;
  maxParticipants: number;
}

interface EventListProps {
  isAdmin: boolean;
}

const EventList: React.FC<EventListProps> = ({ isAdmin }) => {
  const [events, setEvents] = useState<EventProps[]>([]);
  const [error, setError] = useState("");
  const [isUpdatePopupVisible, setIsUpdatePopupVisible] = useState(false);
  const [selectedEvent, setSelectedEvent] = useState<EventProps | null>(null);
  const navigate = useNavigate();

  // Fetch events
  const fetchEvents = async () => {
    try {
      const response = await fetch("http://localhost:8080/events");
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      const data: EventProps[] = await response.json();
      setEvents(data);
    } catch (err) {
      console.error("Error fetching events:", err);
      setError("Failed to fetch events.");
    }
  };

  // Delete an event (Admin Only)
  const deleteEvent = async (id: number) => {
    const userId = localStorage.getItem("userId");
    try {
      const response = await fetch(`http://localhost:8080/events/${id}?userId=${userId}`, {
        method: "DELETE",
      });
      if (response.ok) {
        setEvents(events.filter((event) => event.id !== id));
      } else {
        alert("Failed to delete event.");
      }
    } catch (err) {
      console.error("Error deleting event:", err);
    }
  };

  const handleBackToTasks = () => {
    navigate("/index");
  };

  // Show update popup
  const showUpdatePopup = (event: EventProps) => {
    setSelectedEvent(event);
    setIsUpdatePopupVisible(true);
  };

  // Handle update event
  const handleUpdateEvent = async (updatedEvent: EventProps) => {
    const userId = localStorage.getItem("userId");
    try {
      const response = await fetch(`http://localhost:8080/events/${updatedEvent.id}?userId=${userId}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(updatedEvent),
      });
      if (response.ok) {
        const updatedEvents = events.map((event) =>
            event.id === updatedEvent.id ? { ...event, ...updatedEvent } : event
        );
        setEvents(updatedEvents);
      } else {
        alert("Failed to update event.");
      }
    } catch (err) {
      console.error("Error updating event:", err);
    } finally {
      setIsUpdatePopupVisible(false);
    }
  };

  useEffect(() => {
    fetchEvents();
  }, []);

  if (error) {
    return <p>{error}</p>;
  }

  return (
      <div className="event-list-container">
        <div className="event-list">
          {events.map((event) => (
              <Event
                  key={event.id}
                  event={event}
                  isAdmin={isAdmin}
                  onDelete={deleteEvent}
                  onUpdate={showUpdatePopup}
              />
          ))}
        </div>
        <UpdateEventPopup
            isVisible={isUpdatePopupVisible}
            onClose={() => setIsUpdatePopupVisible(false)}
            event={selectedEvent}
            onUpdate={handleUpdateEvent}
        />
        <div className="back-to-tasks-button-container">
          <button className="back-to-tasks-btn" onClick={handleBackToTasks}>
            Back to Tasks
          </button>
        </div>
      </div>
  );
};

export default EventList;
