import { useEffect, useState } from "react";
import AddEvent from "./AddEvent";
import EventList from "./EventList";
import NavigationBar from "./Navbar.tsx";
import Footer from "../../Footer.tsx";

export default function Index() {
  const [isAdmin, setIsAdmin] = useState(false);
  const [isAddEventVisible, setIsAddEventVisible] = useState(false);

  useEffect(() => {
    // Check if the user is admin based on userRole stored in localStorage
    const userRole = localStorage.getItem("userRole");
    setIsAdmin(userRole === "ADMIN");
  }, []);

  const toggleAddEventVisibility = () => {
    setIsAddEventVisible(!isAddEventVisible);
  };

  return (
    <>
      <NavigationBar
        isAdmin={isAdmin}
        onAddTaskClick={toggleAddEventVisibility}
      />
      <main className="container">
        <AddEvent
          isAdmin={isAdmin}
          isVisible={isAddEventVisible}
          onClose={() => setIsAddEventVisible(false)}
        />
        <EventList isAdmin={isAdmin} />
      </main>
      <Footer />
    </>
  );
}
