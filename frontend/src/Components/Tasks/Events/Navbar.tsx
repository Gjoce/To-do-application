import { useNavigate } from "react-router-dom";
import React from "react";

interface NavigationBarProps {
  onAddTaskClick: () => void;
  isAdmin: boolean;
}

const NavigationBar: React.FC<NavigationBarProps> = ({
  onAddTaskClick,
  isAdmin,
}) => {
  const navigate = useNavigate();

  const handleViewMyEvents = () => {
    const userId = localStorage.getItem("userId");
    if (!userId) {
      alert("User ID not found. Please log in.");
      return;
    }
    navigate(`/user-events/${userId}`); // Navigate to a new route for user-specific events
  };

  const handleLogout = () => {
    // Clear localStorage data
    localStorage.clear(); // Clear all user-related data
    // Redirect to login page
    navigate("/index", { replace: true });
    setTimeout(() => {
      window.location.href = "/index";
    }, 0);
  };

  return (
    <header>
      <nav>
        <h1 className="nav--h1">To-do List</h1>
        <ul className="nav--ul">
          {isAdmin ? (
            <>
              <li>
                <button className="add-task-btn" onClick={onAddTaskClick}>
                  Add Event
                </button>
              </li>
            </>
          ) : (
            <>
              <li>
                <button className="add-task-btn" onClick={handleViewMyEvents}>
                  View My Events
                </button>
              </li>
            </>
          )}
          <li>
            <button className="add-task-btn" onClick={handleLogout}>
              Logout
            </button>
          </li>
        </ul>
      </nav>
    </header>
  );
};

export default NavigationBar;
