import React, { useEffect, useState } from "react";
import Event from "./Event";
import "./EventList.css";

interface Event {
  id: number;
  name: string;
  description?: string;
  startTime: string;
  endTime: string;
  location: string;
  maxParticipants: number;
}

const EventList = () => {
  const [events, setEvents] = useState<Event[]>([]);

  useEffect(() => {
    const fetchEvents = async () => {
      try {
        const response = await fetch("http://localhost:8080/events");
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const data: Event[] = await response.json();
        setEvents(data);
      } catch (error) {
        console.error("Fetch failed:");
      }
    };

    fetchEvents();
  }, []);

  return (
    <div className="event-list">
      {events.map((event) => (
        <Event key={event.id} event={event} />
      ))}
    </div>
  );
};

export default EventList;
