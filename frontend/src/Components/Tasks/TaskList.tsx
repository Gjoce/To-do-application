import React, { useEffect, useState } from "react";
import TaskCard from "./Task";
import UpdateTaskPopup from "./UpdateTaskPopup";
import "bootstrap/dist/css/bootstrap.min.css";

interface Task {
  id: number;
  title: string;
  description?: string;
  status: string;
  priority: string;
  favorite: boolean;
  dueDate: string;
}

export default function TaskList() {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [filteredTasks, setFilteredTasks] = useState<Task[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [selectedTask, setSelectedTask] = useState<Task | null>(null);
  const [isPopupVisible, setPopupVisible] = useState(false);
  const [filter, setFilter] = useState("all");

  useEffect(() => {
    const fetchTasks = async () => {
      const userId = localStorage.getItem("userId");
      if (!userId) {
        setError("User not logged in.");
        setLoading(false);
        return;
      }

      try {
        const response = await fetch(
            `http://localhost:8080/api/users/${userId}/tasks`
        );
        if (!response.ok) {
          throw new Error("Failed to fetch tasks");
        }
        const data = await response.json();
        setTasks(data);
        setLoading(false);
      } catch (err) {
        setError(err instanceof Error ? err.message : "An error occurred");
        setLoading(false);
      }
    };

    fetchTasks();
  }, []);

  useEffect(() => {
    const filtered =
        filter === "completed"
            ? tasks.filter((task) => task.status === "FINISHED")
            : filter === "pending"
                ? tasks.filter((task) => task.status === "PENDING")
                : filter === "in progress"
                    ? tasks.filter((task) => task.status === "RUNNING")
                    : filter === "Favourites"
                        ? tasks.filter((task) => task.favorite) // Filter for favorite tasks
                        : tasks;

    setFilteredTasks(filtered);
  }, [filter, tasks]);

  const handleFilterChange = (newFilter: string) => setFilter(newFilter);

  const handleFavoriteToggle = async (id: number, newFavorite: boolean) => {
    try {
      const response = await fetch(
          `http://localhost:8080/api/tasks/${id}/favorite?isFavorite=${newFavorite}`,
          {
            method: "PUT",
          }
      );
      if (!response.ok) {
        throw new Error("Failed to update favorite status");
      }
      setTasks((prevTasks) =>
          prevTasks.map((task) =>
              task.id === id ? { ...task, favorite: newFavorite } : task
          )
      );
    } catch (error) {
      console.error("Error updating favorite status:", error);
    }
  };

  const handleEditClick = (task: Task) => {
    setSelectedTask(task);
    setPopupVisible(true);
  };

  const handlePopupClose = () => {
    setPopupVisible(false);
    setSelectedTask(null);
  };

  const handleDeleteTask = async (id: number) => {
    try {
      const response = await fetch(`http://localhost:8080/api/tasks/${id}`, {
        method: "DELETE",
      });
      if (!response.ok) {
        throw new Error("Failed to delete task");
      }
      setTasks((prevTasks) => prevTasks.filter((task) => task.id !== id));
    } catch (error) {
      console.error("Error deleting task:", error);
    }
  };

  if (loading) return <div className="text-center">Loading tasks...</div>;
  if (error) return <div className="text-center text-danger">Error: {error}</div>;

  return (
      <div className="container mt-4">
        <div className="d-flex justify-content-center mb-4">
          {["all", "completed", "pending", "in progress", "Favourites"].map(
              (type) => (
                  <button
                      key={type}
                      className={`btn me-2 ${
                          filter === type ? "btn-primary" : "btn-outline-primary"
                      }`}
                      onClick={() => handleFilterChange(type)}
                  >
                    {type.charAt(0).toUpperCase() + type.slice(1)}
                  </button>
              )
          )}
        </div>
        <div className="row">
          {filteredTasks.map((task) => (
              <div className="col-md-4 mb-4" key={task.id}>
                <TaskCard
                    id={task.id}
                    title={task.title}
                    description={task.description}
                    status={task.status}
                    priority={task.priority}
                    initialFavorite={task.favorite}
                    dueDate={task.dueDate}
                    onDelete={() => handleDeleteTask(task.id)}
                    onEdit={() => handleEditClick(task)}
                    onFavouriteToggle={(newFavorite) =>
                        handleFavoriteToggle(task.id, newFavorite)
                    }
                />
              </div>
          ))}
        </div>
        {selectedTask && (
            <UpdateTaskPopup
                isVisible={isPopupVisible}
                onClose={handlePopupClose}
                task={selectedTask}
                onUpdate={() => {}}
            />
        )}
      </div>
  );
}
