import React, { useState, useEffect } from "react";
import "../../../UpdateEventPopup.css"; // Add your styles for the popup

interface UpdateEventPopupProps {
  isVisible: boolean;
  onClose: () => void;
  event: {
    id: number;
    name: string;
    description?: string;
    startTime: string;
    endTime: string;
    location: string;
    maxParticipants: number;
  } | null; // Allow event to be nullable to handle state properly
  onUpdate: (updatedEvent: any) => void;
}

const UpdateEventPopup: React.FC<UpdateEventPopupProps> = ({
                                                             isVisible,
                                                             onClose,
                                                             event,
                                                             onUpdate
                                                           }) => {
  // State for form fields
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [location, setLocation] = useState("");
  const [maxParticipants, setMaxParticipants] = useState(1);

  // Update state values when `event` changes or when `isVisible` becomes true
  useEffect(() => {
    if (event && isVisible) {
      setName(event.name);
      setDescription(event.description || "");
      setStartTime(event.startTime);
      setEndTime(event.endTime);
      setLocation(event.location);
      setMaxParticipants(event.maxParticipants);
    }
  }, [event, isVisible]);

  // Handle form submission
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!event) return;

    const updatedEvent = {
      id: event.id,
      name,
      description,
      startTime,
      endTime,
      location,
      maxParticipants,
    };
    onUpdate(updatedEvent); // Call the parent update function
    onClose(); // Close the popup
  };

  if (!isVisible) {
    return null; // Do not render the popup if not visible
  }

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
            <h2>Update Event</h2>
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
                    className="form-control"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                />
              </div>
              <div className="form-group">
                <label htmlFor="description">Description</label>
                <textarea
                    id="description"
                    className="form-control"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                ></textarea>
              </div>
              <div className="form-group">
                <label htmlFor="startTime">Start Time</label>
                <input
                    type="datetime-local"
                    id="startTime"
                    className="form-control"
                    value={startTime.substring(0, 16)}
                    onChange={(e) => setStartTime(e.target.value)}
                    required
                />
              </div>
              <div className="form-group">
                <label htmlFor="endTime">End Time</label>
                <input
                    type="datetime-local"
                    id="endTime"
                    className="form-control"
                    value={endTime.substring(0, 16)}
                    onChange={(e) => setEndTime(e.target.value)}
                    required
                />
              </div>
              <div className="form-group">
                <label htmlFor="location">Location</label>
                <input
                    type="text"
                    id="location"
                    className="form-control"
                    value={location}
                    onChange={(e) => setLocation(e.target.value)}
                    required
                />
              </div>
              <div className="form-group">
                <label htmlFor="maxParticipants">Max Participants</label>
                <input
                    type="number"
                    id="maxParticipants"
                    className="form-control"
                    min="1"
                    value={maxParticipants}
                    onChange={(e) => setMaxParticipants(Number(e.target.value))}
                    required
                />
              </div>
              <div className="submit-button">
                <button type="submit" className="btn-submit">
                  Update Event
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
  );
};

export default UpdateEventPopup;
