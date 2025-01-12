import React, { useState } from "react";
import "../../TaskCard.css";

interface TaskCardProps {
    id: number; // Task ID from the backend
    title: string;
    description?: string;
    status: string;
    priority: string;
    dueDate: string;
    initialFavorite: boolean; // Initial favorite status
    onDelete: () => void;
    onEdit: () => void;
    onFavouriteToggle: (newFavorite: boolean) => void; // Toggle favorite status
}

export default function TaskCard({
                                     id,
                                     title,
                                     description,
                                     status,
                                     priority,
                                     dueDate,
                                     initialFavorite,
                                     onDelete,
                                     onEdit,
                                     onFavouriteToggle,
                                 }: TaskCardProps) {
    const [favorite, setFavorite] = useState(initialFavorite); // State for favorite status
    const [loading, setLoading] = useState(false); // Loading state for toggling

    // Handle favorite toggle and send the updated status to the parent
    const handleFavoriteToggle = async (e: React.MouseEvent) => {
        e.stopPropagation(); // Prevent parent click
        if (loading) return; // Prevent duplicate requests
        setLoading(true);
        try {
            await onFavouriteToggle(!favorite); // Call parent function with the new state
            setFavorite((prev) => !prev); // Update local state
        } catch (error) {
            console.error("Error toggling favorite status:", error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="task-card" onClick={onEdit}>
            <div className="task-card-header">
                <strong className="task-title">{title}</strong>
                <span className={`priority-badge badge-${priority.toLowerCase()}`}>
          {priority}
        </span>
                <img
                    src={
                        favorite
                            ? "/public/like-icon.png" // Filled heart
                            : "/public/no-like-icon.png" // Outlined heart
                    }
                    alt={favorite ? "Liked" : "Not Liked"}
                    className={`favourite-icon ${loading ? "loading" : ""}`}
                    onClick={handleFavoriteToggle} // Toggle on click
                />
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
