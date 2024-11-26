import React, { useState } from "react";
import "../../../UpdateEventPopup.css";

interface AddEventProps {
    isAdmin: boolean;
    isVisible: boolean;
    onClose: () => void;
}

const AddEvent: React.FC<AddEventProps> = ({ isAdmin, isVisible, onClose }) => {
    const [event, setEvent] = useState({
        name: "",
        description: "",
        startTime: "",
        endTime: "",
        location: "",
        maxParticipants: "",
    });


    if (!isAdmin || !isVisible) {
        return null;
    }

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        setEvent({ ...event, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        // Fetch userId from localStorage (or any other method if necessary)
        const userId = localStorage.getItem("userId");

        if (!userId) {
            alert("User ID not found.");
            return;
        }

        // Make the POST request to the backend
        try {
            const response = await fetch(`http://localhost:8080/events?userId=${userId}`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(event), // Send event data as JSON
            });

            if (response.ok) {
                setEvent({
                    name: "",
                    description: "",
                    startTime: "",
                    endTime: "",
                    location: "",
                    maxParticipants: "",
                });
                onClose();
            } else {
                alert("Failed to add event.");
            }
        } catch (err) {
            console.error("Error in creating event:", err);
            alert("An error occurred while adding the event.");
        }
    };

    return (
        <div
            className={`popup-overlay ${isVisible ? "active" : ""}`}
            onClick={onClose}
        >
            <div
                className={`popup-form ${isVisible ? "active" : ""}`}
                onClick={(e) => e.stopPropagation()}
            >
                <div className="popup-header">
                    <h2>Add Event</h2>
                    <button className="close-btn" onClick={onClose}>
                        X
                    </button>
                </div>
                <div className="popup-body">
                    <form onSubmit={handleSubmit}>
                        <div className="form-group">
                            <label htmlFor="name">Event Name</label>
                            <input
                                type="text"
                                id="name"
                                name="name"
                                className="form-control"
                                value={event.name}
                                onChange={handleChange}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="description">Description</label>
                            <textarea
                                id="description"
                                name="description"
                                className="form-control"
                                value={event.description}
                                onChange={handleChange}
                            ></textarea>
                        </div>
                        <div className="form-group">
                            <label htmlFor="startTime">Start Time</label>
                            <input
                                type="datetime-local"
                                id="startTime"
                                name="startTime"
                                className="form-control"
                                value={event.startTime}
                                onChange={handleChange}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="endTime">End Time</label>
                            <input
                                type="datetime-local"
                                id="endTime"
                                name="endTime"
                                className="form-control"
                                value={event.endTime}
                                onChange={handleChange}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="location">Location</label>
                            <input
                                type="text"
                                id="location"
                                name="location"
                                className="form-control"
                                value={event.location}
                                onChange={handleChange}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="maxParticipants">Max Participants</label>
                            <input
                                type="number"
                                id="maxParticipants"
                                name="maxParticipants"
                                className="form-control"
                                min="1"
                                value={event.maxParticipants}
                                onChange={(e) => setEvent({ ...event, maxParticipants: Number(e.target.value) })}
                                required
                            />
                        </div>
                        <div className="submit-button">
                            <button type="submit" className="btn-submit">
                                Add Event
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default AddEvent;
