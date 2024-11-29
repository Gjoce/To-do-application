import { useNavigate } from "react-router-dom";
import React from "react";

interface NavigationBarProps {
  onAddTaskClick: () => void;
  isAdmin: boolean;
}

const NavigationBar: React.FC<NavigationBarProps> = ({ onAddTaskClick, isAdmin }) => {
  const navigate = useNavigate();

  const handleViewEvents = () => {
    navigate("/events");
  };

  const handleApplyEvent = () => {
    navigate("/apply-event");
  }

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
            <li>
              {isAdmin ? (
                  <>
                    <button className="add-task-btn" onClick={onAddTaskClick}>
                      Add Event
                    </button>
                    <button className="add-task-btn" onClick={handleViewEvents}>
                      View My Events
                    </button>
                  </>
              ) : (
                  <>
                    <button className="add-task-btn" onClick={handleViewEvents}>
                      View My Events
                    </button>
                    <button className="add-task-btn" onClick={handleApplyEvent}>
                      Apply to Event
                    </button>
                  </>
              )}
            </li>
            <li>
              <button className="logout-btn" onClick={handleLogout}>
                Logout
              </button>
            </li>
          </ul>
        </nav>
      </header>
  );
};

export default NavigationBar;
