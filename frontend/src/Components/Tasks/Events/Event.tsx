import React from "react";
import "../../../Event.css";
import { useNavigate } from "react-router-dom";

interface EventProps {
    id: number;
    name: string;
    description?: string;
    startTime: string;
    endTime: string;
    location: string;
    maxParticipants: number;
    currentParticipants: number;
    applied?: boolean;  // This will track if the user has applied to the event
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
    const navigate = useNavigate(); // Initialize navigate hook

    // Handle navigating back to the tasks page
    const handleBacktoTasks = () => {
        navigate("/index");
    };

    return (
        <div className="event-list-container">
            <div className="event-list">
                <div className="event-card">
                    <div className="event-card-header">
                        <span className="event-title">{event.name}</span>
                        <span className="event-badge">Event</span>
                    </div>
                    <div className="event-card-body">
                        <p className="event-desc">{event.description || "No description provided."}</p>
                        <p><strong>Start:</strong> {new Date(event.startTime).toLocaleString()}</p>
                        <p><strong>End:</strong> {new Date(event.endTime).toLocaleString()}</p>
                        <p><strong>Location:</strong> {event.location}</p>
                        <p><strong>Max Participants:</strong> {event.maxParticipants}</p>
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
                            !event.applied && (
                                <button
                                    className="apply-btn"
                                    disabled={event.currentParticipants >= event.maxParticipants}
                                    onClick={() => onApply(event.id)}
                                >
                                    Apply
                                </button>
                            )
                        )}
                    </div>
                </div>
            </div>

            {/* Single Back to Tasks button outside the event cards, at the bottom of the page */}
            <div className="back-to-tasks-button-container">
                <button
                    className="back-to-tasks-btn"
                    onClick={handleBacktoTasks}
                >
                    Back to Tasks
                </button>
            </div>
        </div>
    );
};

export default Event;
