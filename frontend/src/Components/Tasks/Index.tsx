import NavigationBar from "../Navbar";
import PopupForm from "./AddTask";
import { useState } from "react";
import TaskList from "./TaskList";
import Footer from "../Footer";

export default function Index() {
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
