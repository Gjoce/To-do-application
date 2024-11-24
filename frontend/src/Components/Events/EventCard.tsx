interface EventCardProps {
    title: string;
    description?: string;
    date: string;
    onDelete: () => void;
    onEdit: () => void;
}

export default function EventCard({
                                      title,
                                      description,
                                      date,
                                      onDelete,
                                      onEdit,
                                  }: EventCardProps) {
    return (
        <div className="event-card" onClick={onEdit}>
            <div className="event-card-header">
                <strong className="event-title">{title}</strong>
            </div>
            <div className="event-card-body">
                <p className="event-desc">
                    <strong>Description:</strong>{" "}
                    {description || "No description provided."}
                </p>
                <p>
                    <strong>Date:</strong> {new Date(date).toLocaleString()}
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
