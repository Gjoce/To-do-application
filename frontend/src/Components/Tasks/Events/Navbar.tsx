import { useNavigate } from "react-router-dom";

interface NavigationBarProps {
  onAddTaskClick: () => void;
  isAdmin: boolean;
}

function NavigationBar({ onAddTaskClick, isAdmin }: NavigationBarProps) {
  const navigate = useNavigate();

  const handleViewEvents = () => {
    navigate("/events");
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
              <button className="add-task-btn" onClick={handleViewEvents}>
                View My Events
              </button>
            )}
          </li>
        </ul>
      </nav>
    </header>
  );
}

export default NavigationBar;
