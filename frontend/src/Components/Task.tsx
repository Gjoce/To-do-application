import "../TaskCard.css";

interface TaskCardProps {
  title: string;
  description?: string;
  status: string;
  priority: string;
  dueDate: string;
  onDelete: () => void;
  onEdit: () => void;
}

export default function TaskCard({
  title,
  description,
  status,
  priority,
  dueDate,
  onDelete,
  onEdit,
}: TaskCardProps) {
  return (
    <div className="task-card" onClick={onEdit}>
      <div className="task-card-header">
        <strong className="task-title">{title}</strong>
        <span className={`priority-badge badge-${priority.toLowerCase()}`}>
          {priority}
        </span>
      </div>
      <div className="task-card-body">
        <p className="task-desc">
          <strong>Description:</strong>{" "}
          {description || "No description provided."}
        </p>
        <span className={`status-badge badge-${status.toLowerCase()}`}>
          {status}
        </span>
      </div>
      <div className="task-card-footer">
        <p>
          <strong>Due Date:</strong> {new Date(dueDate).toLocaleString()}
        </p>
      </div>
      <button
        className="btn btn-danger"
        onClick={(e) => {
          e.stopPropagation();
          onDelete();
        }}
      >
        Delete
      </button>
    </div>
  );
}
