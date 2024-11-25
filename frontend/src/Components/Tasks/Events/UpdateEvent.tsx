import React, { useState } from "react";
import "./UpdateEventPopup.css"; // Add your styles for the popup

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
  };
  onUpdate: (updatedEvent: any) => void;
}

const UpdateEventPopup: React.FC<UpdateEventPopupProps> = ({
  isVisible,
  onClose,
  event,
  onUpdate,
}) => {
  const [name, setName] = useState(event.name);
  const [description, setDescription] = useState(event.description || "");
  const [startTime, setStartTime] = useState(event.startTime);
  const [endTime, setEndTime] = useState(event.endTime);
  const [location, setLocation] = useState(event.location);
  const [maxParticipants, setMaxParticipants] = useState(event.maxParticipants);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
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
