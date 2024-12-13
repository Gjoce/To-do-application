import React, { useState, useEffect } from "react";
import "../../Navbar.css";

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
  const [attachment, setAttachment] = useState<{
    fileUrl: string;
    fileName: string;
  } | null>(null);

  // Fetch attachment when the component loads
  useEffect(() => {
    const fetchAttachment = async () => {
      try {
        const response = await fetch(
          `http://localhost:8080/api/files/attachments/certain/${task.id}`
        );

        if (response.ok) {
          const data = await response.json();
          console.log("Attachment data:", data);

          // Ensure the response has the expected fileName and create the fileUrl
          if (data && data.fileUrl) {
            setAttachment({
              fileUrl: data.fileUrl, // Use the file URL returned by the backend
              fileName: data.fileName, // If needed for other purposes
            });
          } else {
            console.error("File URL is undefined in the response");
          }
        } else {
          console.error("Failed to fetch attachment details");
        }
      } catch (error) {
        console.error("Error fetching attachment:", error);
      }
    };

    fetchAttachment();
  }, [task.id]);

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

  const handleDownload = () => {
    if (attachment && attachment.fileUrl) {
      // Trigger download by creating a temporary <a> element and clicking it
      const link = document.createElement("a");
      link.href = attachment.fileUrl;
      link.download = attachment.fileName; // Optional: specify the filename
      link.target = "_blank"; // Optional: open in new tab if needed
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    } else {
      console.error("No attachment URL available for download");
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
          <h2>Update Task</h2>
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

          {/* Display the attachment if it exists */}
          {attachment && (
            <div className="attachment-section">
              <h3>Attachment:</h3>
              <button className="btn-download" onClick={handleDownload}>
                Download Attachment
              </button>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default UpdateTaskPopup;
