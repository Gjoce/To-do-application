import React from "react";
import "../../../Event.css";

interface EventProps {
    id: number;
    name: string;
    description?: string;
    startTime: string;
    endTime: string;
    location: string;
    maxParticipants: number;
}

interface EventComponentProps {
    event: EventProps;
    isAdmin: boolean;
    onDelete: (id: number) => void;
    onUpdate: (event: EventProps) => void;
}

const Event: React.FC<EventComponentProps> = ({ event, isAdmin, onDelete, onUpdate }) => (
    <div className="event-card">
        <div className="event-card-header">
            <span className="event-title">{event.name}</span>
            <span className="event-badge badge-high">Event</span>
        </div>
        <div className="event-card-body">
            <p className="event-desc">{event.description || "No description provided."}</p>
        </div>
        <div className="event-card-footer">
            <p>
                <strong>Start:</strong> {new Date(event.startTime).toLocaleString()} <br />
                <strong>End:</strong> {new Date(event.endTime).toLocaleString()}
            </p>
            <p>
                <strong>Location:</strong> {event.location}
            </p>
            <p>
                <strong>Max Participants:</strong> {event.maxParticipants}
            </p>
            {isAdmin && (
                <div className="admin-actions">
                    <button className="update-btn" onClick={() => onUpdate(event)}>
                        Update
                    </button>
                    <button className="delete-btn" onClick={() => onDelete(event.id)}>
                        Delete
                    </button>

                </div>
            )}
        </div>
    </div>
);

export default Event;
