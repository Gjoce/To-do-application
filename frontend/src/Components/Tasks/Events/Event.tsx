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
    currentParticipants: number;
    applied?: boolean;
}

interface EventComponentProps {
    event: EventProps;
    isAdmin: boolean;
    onDelete: (id: number) => void;
    onApply: (id: number) => void;
    onUpdate: (event: EventProps) => void;
    onViewApplicants: (eventId: number) => void;
}

const Event: React.FC<EventComponentProps> = ({
                                                  event,
                                                  isAdmin,
                                                  onDelete,
                                                  onApply,
                                                  onUpdate,
                                                  onViewApplicants,
                                              }) => {
    return (
        <div className="event-card">
            <div className="event-card-header">
                <span className="event-title">{event.name}</span>
                <span className="event-badge">Event</span>
            </div>
            <div className="event-card-body">
                <p className="event-desc">{event.description || "No description provided."}</p>
                <p>
                    <strong>Start:</strong> {new Date(event.startTime).toLocaleString()}
                </p>
                <p>
                    <strong>End:</strong> {new Date(event.endTime).toLocaleString()}
                </p>
                <p>
                    <strong>Location:</strong> {event.location}
                </p>
                <p>
                    <strong>Max Participants:</strong> {event.maxParticipants}
                </p>
                {(event.applied || isAdmin) && (
                    <p>
                        <strong>Remaining Slots:</strong>{" "}
                        {event.maxParticipants - event.currentParticipants}
                    </p>
                )}
            </div>
            <div className="event-card-footer">
                {isAdmin ? (
                    <div className="admin-actions">
                        <button className="view-applicants-btn" onClick={() => onViewApplicants(event.id)}>
                            View Applicants
                        </button>
                        <button className="update-btn" onClick={() => onUpdate(event)}>
                            Update
                        </button>
                        <button className="delete-btn" onClick={() => onDelete(event.id)}>
                            Delete
                        </button>
                    </div>
                ) : (
                    !event.applied && ( // Hide the "Apply" button for already applied events
                        <button
                            className="apply-btn"
                            disabled={event.maxParticipants <= event.currentParticipants}
                            onClick={() => onApply(event.id)}
                        >
                            Apply
                        </button>
                    )
                )}
            </div>
        </div>
    );
};

export default Event;
