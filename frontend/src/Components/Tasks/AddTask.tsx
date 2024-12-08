import "../../Navbar.css";
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
  const [attachedFile, setAttachedFile] = useState<File | null>(null);
  const [uploadedFileUrl, setUploadedFileUrl] = useState<string | null>(null); // To store the file URL
  const [viewAttachment, setViewAttachment] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const userId = localStorage.getItem("userId");

    const newTask = {
      title,
      description,
      status,
      priority,
      dueDate,
      userId,
    };

    const formData = new FormData();
    formData.append("taskData", JSON.stringify(newTask));
    if (attachedFile) {
      formData.append("file", attachedFile);
    }

    try {
      const response = await fetch(
          `http://localhost:8080/api/tasks?userId=${userId}`,
          {
            method: "POST",
            body: formData,
          }
      );

      if (!response.ok) {
        throw new Error("Failed to create task");
      }

      const result = await response.json(); // Assuming API returns the uploaded file's URL
      setUploadedFileUrl(result.fileUrl || null);

      // Reset form fields after successful submission
      setTitle("");
      setDescription("");
      setStatus("PENDING");
      setPriority("LOW");
      setDueDate("");
      setAttachedFile(null);
      setViewAttachment(false);

      onClose(); // Close the popup
    } catch (error) {
      console.error("Error adding task:", error);
    }
  };

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0] || null;
    setAttachedFile(file);
  };

  const handleViewAttachment = () => {
    setViewAttachment(!viewAttachment);
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
              <div className="form-group">
                <label htmlFor="file">Attach File</label>
                <input
                    type="file"
                    id="file"
                    className="form-control"
                    onChange={handleFileChange}
                />
              </div>
              <div className="submit-button">
                <button type="submit" className="btn-submit">
                  Add Task
                </button>
              </div>
            </form>
            {uploadedFileUrl && (
                <div className="attachment-section">
                  <button
                      className="btn-attachment"
                      onClick={handleViewAttachment}
                  >
                    {viewAttachment ? "Hide Attachment" : "View Attachment"}
                  </button>
                  {viewAttachment && (
                      <div className="attachment-display">
                        <a href={uploadedFileUrl} target="_blank" rel="noopener noreferrer">
                          Download Attached File
                        </a>
                      </div>
                  )}
                </div>
            )}
          </div>
        </div>
      </div>
  );
}

export default PopupForm;
