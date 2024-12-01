import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Login from "./Components/Login";
import Register from "./Components/Register";
import Index from "./Components/Tasks/Index";
import EventList from "./Components/Tasks/Events/Index";
import ApplyEvent  from "./Components/Tasks/Events/ApplyEvent.tsx"
import UserEvents from "./Components/Tasks/Events/UserEvents.tsx";

const App: React.FC = () => {
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);

  useEffect(() => {
    const user = localStorage.getItem("userId");
    setIsAuthenticated(!!user);
  }, []);

  return (
    <Router>
      <Routes>
        <Route
          path="/"
          element={<Login setIsAuthenticated={setIsAuthenticated} />}
        />
        <Route path="/register" element={<Register />} />
        <Route
          path="/index"
          element={
            isAuthenticated ? (
              <Index />
            ) : (
              <Login setIsAuthenticated={setIsAuthenticated} />
            )
          }
        />
        <Route path="/events" element={<EventList />} />
        <Route path="/apply-event" element={<ApplyEvent />} />
          <Route path="/user-events/:userId" element={<UserEvents />} />

          {/* New Route */}
      </Routes>
    </Router>
  );
};

export default App;
