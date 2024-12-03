import React from "react";
import "../../../Event.css";



interface EventDetailsProps {
    id: number;
    name: string;
    description?: string;
    startTime: string;
    endTime: string;
    location: string;
    maxParticipants: number;
}

const EventDetails: React.FC<EventDetailsProps> = ({
                                                       name,
                                                       description,
                                                       startTime,
                                                       endTime,
                                                       location,
                                                       maxParticipants,
                                                   }) => {
    return (
        <div className="event-card">
            <div className="event-card-header">
                <span className="event-title">{name}</span>
                <span className="event-badge">Event</span>
            </div>
            <div className="event-card-body">
                <p className="event-desc">{description || "No description provided."}</p>
                <p><strong>Start:</strong> {new Date(startTime).toLocaleString()}</p>
                <p><strong>End:</strong> {new Date(endTime).toLocaleString()}</p>
                <p><strong>Location:</strong> {location}</p>
                <p><strong>Max Participants:</strong> {maxParticipants}</p>
            </div>
            <div className="event-card-footer">
                {/* No Apply button is rendered for applied events */}
            </div>
        </div>
    );
};

export default EventDetails;
