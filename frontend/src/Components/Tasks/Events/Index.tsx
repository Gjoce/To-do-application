import React, { useEffect, useState } from "react";
import EventList from "./EventLis";
import Footer from "../../Footer";
import NavigationBar from "./Navbar";

export default function Index() {
  const [isFormVisible, setIsFormVisible] = useState(false);
  const [isAdmin, setIsAdmin] = useState(false);

  useEffect(() => {
    // Fetch the userRole from localStorage
    const userRole = localStorage.getItem("userRole");
    setIsAdmin(userRole === "ADMIN"); // Update state based on userRole
  }, []);

  const toggleFormVisibility = () => {
    setIsFormVisible(!isFormVisible);
  };

  return (
    <>
      {/* Pass the isAdmin prop to NavigationBar */}
      <NavigationBar onAddTaskClick={toggleFormVisibility} isAdmin={isAdmin} />

      <main className="container">
        <EventList />
      </main>

      <Footer />
    </>
  );
}
