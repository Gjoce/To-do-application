import React, { useState } from "react";
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

const fetchApplicants = async (eventId: number) => {
    try {
        const response = await fetch(`http://localhost:8080/events/${eventId}/applicants`);
        if (!response.ok) throw new Error("Failed to fetch applicants");
        return await response.json();
    } catch (err) {
        console.error(err);
        return [];
    }
};

// ApplicantsPopup Component (Placed Inside Event.tsx)
const ApplicantsPopup: React.FC<{ isVisible: boolean; onClose: () => void; applicants: any[] }> = ({
                                                                                                       isVisible,
                                                                                                       onClose,
                                                                                                       applicants,
                                                                                                   }) => {
    if (!isVisible) return null;

    return (
        <div className={`popup-overlay ${isVisible ? "active" : ""}`}>
            <div className={`popup-form ${isVisible ? "active" : ""}`}>
                <div className="popup-header">
                    <h2>Applicants</h2>
                    <button className="close-btn" onClick={onClose}>
                        &times;
                    </button>
                </div>
                <ul className="applicants-list">
                    {applicants.map((applicant, index) => (
                        <li className="applicants-list-item" key={index}>
                            <span>{applicant.name}</span> - {applicant.email}
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
};

// Main Event Component
const Event: React.FC<EventComponentProps> = ({ event, isAdmin, onDelete, onUpdate }) => {
    const [isApplicantsPopupVisible, setApplicantsPopupVisible] = useState(false);
    const [applicants, setApplicants] = useState<any[]>([]);

    const showApplicantsPopup = async () => {
        const fetchedApplicants = await fetchApplicants(event.id);
        setApplicants(fetchedApplicants);
        setApplicantsPopupVisible(true);
    };

    return (
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
                        <button className="view-applicants-btn" onClick={showApplicantsPopup}>
                            View Applicants
                        </button>
                    </div>
                )}
            </div>
            <ApplicantsPopup
                isVisible={isApplicantsPopupVisible}
                onClose={() => setApplicantsPopupVisible(false)}
                applicants={applicants}
            />
        </div>
    );
};

export default Event;
