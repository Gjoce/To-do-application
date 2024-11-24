import { useNavigate } from "react-router-dom";

interface NavigationBarProps {
  onAddTaskClick: () => void;
}

function NavigationBar({ onAddTaskClick }: NavigationBarProps) {
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
            <button className="add-task-btn" onClick={handleViewEvents}>
              View Events
            </button>
          </li>
          <li>
            <button className="add-task-btn" onClick={onAddTaskClick}>
              Add Task
            </button>
          </li>
        </ul>
      </nav>
    </header>
  );
}

export default NavigationBar;
