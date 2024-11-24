import TaskCard from "./Task";
import UpdateTaskPopup from "./UpdateTaskPopup";
import "bootstrap/dist/css/bootstrap.min.css";
import { useEffect, useState } from "react";

interface Task {
  id: number;
  title: string;
  description?: string;
  status: string;
  priority: string;
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
        console.error("User ID is missing or the user is not logged in.");
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
      } catch (error) {
        setLoading(false);
        setError(error instanceof Error ? error.message : "An error occurred");
      }
    };

    fetchTasks();
  }, []);

  useEffect(() => {
    let filtered;
    if (filter === "completed") {
      filtered = tasks.filter((task) => task.status === "FINISHED");
    } else if (filter === "pending") {
      filtered = tasks.filter((task) => task.status === "PENDING");
    } else if (filter === "in progress") {
      filtered = tasks.filter((task) => task.status === "RUNNING");
    } else {
      filtered = tasks;
    }
    setFilteredTasks(filtered);
  }, [filter, tasks]);

  const handleFilterChange = (newFilter: string) => {
    setFilter(newFilter);
  };

  const handleUpdateTask = async (updatedTask: Task) => {
    try {
      const response = await fetch(
        `http://localhost:8080/api/tasks/${updatedTask.id}`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(updatedTask),
        }
      );
      if (!response.ok) {
        throw new Error("Failed to update task");
      }
      setTasks((prevTasks) =>
        prevTasks.map((task) =>
          task.id === updatedTask.id ? updatedTask : task
        )
      );
    } catch (error) {
      console.error("Error updating task:", error);
    }
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

  const handleEditClick = (task: Task) => {
    setSelectedTask(task);
    setPopupVisible(true);
  };

  const handlePopupClose = () => {
    setPopupVisible(false);
    setSelectedTask(null);
  };

  if (loading) {
    return <div className="text-center">Loading tasks...</div>;
  }

  if (error) {
    return <div className="text-center text-danger">Error: {error}</div>;
  }

  return (
    <div className="container mt-4">
      <div className="d-flex justify-content-center mb-4">
        <button
          className={`btn btn-outline-primary me-2 ${
            filter === "all" ? "active" : ""
          }`}
          onClick={() => handleFilterChange("all")}
        >
          All
        </button>
        <button
          className={`btn btn-outline-success me-2 ${
            filter === "completed" ? "active" : ""
          }`}
          onClick={() => handleFilterChange("completed")}
        >
          Completed
        </button>
        <button
          className={`btn btn-outline-warning me-2 ${
            filter === "pending" ? "active" : ""
          }`}
          onClick={() => handleFilterChange("pending")}
        >
          Pending
        </button>
        <button
          className={`btn btn-outline-info ${
            filter === "in progress" ? "active" : ""
          }`}
          onClick={() => handleFilterChange("in progress")}
        >
          In Progress
        </button>
      </div>

      <div className="row">
        {filteredTasks.map((task) => (
          <div className="col-md-4 mb-4" key={task.id}>
            <TaskCard
              title={task.title}
              description={task.description}
              status={task.status}
              priority={task.priority}
              dueDate={task.dueDate}
              onDelete={() => handleDeleteTask(task.id)}
              onEdit={() => handleEditClick(task)}
            />
          </div>
        ))}
      </div>

      {selectedTask && (
        <UpdateTaskPopup
          isVisible={isPopupVisible}
          onClose={handlePopupClose}
          task={selectedTask}
          onUpdate={handleUpdateTask}
        />
      )}
    </div>
  );
}
