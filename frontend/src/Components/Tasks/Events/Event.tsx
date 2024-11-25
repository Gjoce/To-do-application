import React from "react";
import "./Event.css"; // Import the updated CSS file for styling

interface EventProps {
  event: {
    id: number;
    name: string;
    description?: string;
    startTime: string;
    endTime: string;
    location: string;
    maxParticipants: number;
  };
}

const Event: React.FC<EventProps> = ({ event }) => (
  <div className="event-card">
    <div className="event-card-header">
      <span className="event-title">{event.name}</span>
      <span className="event-badge badge-high">Event</span>
    </div>
    <div className="event-card-body">
      <p className="event-desc">
        {event.description || "No description provided."}
      </p>
    </div>
    <div className="event-card-footer">
      <p>
        <strong>Start:</strong> {new Date(event.startTime).toLocaleString()}{" "}
        <br />
        <strong>End:</strong> {new Date(event.endTime).toLocaleString()}
      </p>
      <p>
        <strong>Location:</strong> {event.location}
      </p>
      <p>
        <strong>Max Participants:</strong> {event.maxParticipants}
      </p>
    </div>
  </div>
);

export default Event;
