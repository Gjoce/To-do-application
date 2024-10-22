import NavigationBar from "./Components/Navbar";
import PopupForm from "./Components/AddTask";
import { useState } from "react";
import TaskList from "./Components/TaskList";
import Footer from "./Components/Footer";

export default function App() {
  const [isFormVisible, setIsFormVisible] = useState(false);

  const toggleFormVisibility = () => {
    setIsFormVisible(!isFormVisible);
  };

  return (
    <>
      <NavigationBar onAddTaskClick={toggleFormVisibility} />

      <main className="container">
        <TaskList />
      </main>

      <PopupForm isVisible={isFormVisible} onClose={toggleFormVisibility} />
      <Footer />
    </>
  );
}
