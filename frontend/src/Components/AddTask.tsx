import "../Navbar.css";
import { useState } from "react";

interface PopupFormProps {
  isVisible: boolean;
  onClose: () => void;
}

function PopupForm({ isVisible, onClose }: PopupFormProps) {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [status, setStatus] = useState("PENDING");
  const [priority, setPriority] = useState("LOW");
  const [dueDate, setDueDate] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const newTask = {
      title,
      description,
      status,
      priority,
      dueDate,
    };

    try {
      const response = await fetch("http://localhost:8080/api/tasks", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(newTask),
      });

      if (!response.ok) {
        throw new Error("Failed to create task");
      }

      setTitle("");
      setDescription("");
      setStatus("PENDING");
      setPriority("LOW");
      setDueDate("");

      onClose();
    } catch (error) {
      console.error("Error adding task:", error);
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
          <h2>Add New Task</h2>
          <button className="close-btn" onClick={onClose}>
            X
          </button>
        </div>
        <div className="popup-body">
          <form id="addTaskForm" onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="title">Task Title</label>
              <input
                type="text"
                id="title"
                className="form-control"
                placeholder="Enter task title"
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
                placeholder="Enter task description"
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
                value={dueDate}
                onChange={(e) => setDueDate(e.target.value)}
              />
            </div>
            <div className="submit-button">
              <button type="submit" className="btn-submit">
                Add Task
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}

export default PopupForm;
