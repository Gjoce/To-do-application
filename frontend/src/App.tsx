import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Login from "./Components/Login";
import Register from "./Components/Register";
import Index from "./Components/Tasks/Index";

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
      </Routes>
    </Router>
  );
};

export default App;
