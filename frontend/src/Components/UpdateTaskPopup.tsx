import React, { useState } from "react";
import "../Navbar.css";

interface UpdateTaskPopupProps {
  isVisible: boolean;
  onClose: () => void;
  task: {
    id: number;
    title: string;
    description?: string;
    status: string;
    priority: string;
    dueDate: string;
  };
  onUpdate: (updatedTask: any) => void;
}

const UpdateTaskPopup: React.FC<UpdateTaskPopupProps> = ({
  isVisible,
  onClose,
  task,
  onUpdate,
}) => {
  const [title, setTitle] = useState(task.title);
  const [description, setDescription] = useState(task.description || "");
  const [status, setStatus] = useState(task.status);
  const [priority, setPriority] = useState(task.priority);
  const [dueDate, setDueDate] = useState(task.dueDate);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const updatedTask = {
      id: task.id,
      title,
      description,
      status,
      priority,
      dueDate,
    };
    onUpdate(updatedTask);
    onClose();
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
          <h2>Update Task</h2>
          <button className="close-btn" onClick={onClose}>
            X
          </button>
        </div>
        <div className="popup-body">
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="title">Task Title</label>
              <input
                type="text"
                id="title"
                className="form-control"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
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
              <label htmlFor="status">Status</label>
              <select
                id="status"
                className="form-select"
                value={status}
                onChange={(e) => setStatus(e.target.value)}
              >
                <option value="PENDING">Pending</option>
                <option value="RUNNING">In Progress</option>
                <option value="FINISHED">Finished</option>
              </select>
            </div>
            <div className="form-group">
              <label htmlFor="priority">Priority</label>
              <select
                id="priority"
                className="form-select"
                value={priority}
                onChange={(e) => setPriority(e.target.value)}
              >
                <option value="LOW">Low</option>
                <option value="MEDIUM">Medium</option>
                <option value="HIGH">High</option>
              </select>
            </div>
            <div className="form-group">
              <label htmlFor="dueDate">Due Date</label>
              <input
                type="datetime-local"
                id="dueDate"
                className="form-control"
                value={dueDate.substring(0, 16)}
                onChange={(e) => setDueDate(e.target.value)}
              />
            </div>
            <div className="submit-button">
              <button type="submit" className="btn-submit">
                Update Task
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default UpdateTaskPopup;
