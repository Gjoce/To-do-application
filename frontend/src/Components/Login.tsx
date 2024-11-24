import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "../Login.css";

const Login: React.FC<{
  setIsAuthenticated: React.Dispatch<React.SetStateAction<boolean>>;
}> = ({ setIsAuthenticated }) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const response = await axios.post(
        "http://localhost:8080/api/users/login",
        null,
        {
          params: { email, password },
        }
      );

      const { id: userId, role } = response.data;

      // Store userId and role in localStorage
      localStorage.setItem("userId", userId.toString());
      localStorage.setItem("userRole", role);

      setIsAuthenticated(true);
      setError(null);
      navigate("/index");
    } catch (err) {
      setError("Invalid credentials");
    }
  };

  return (
    <div className="login-container">
      <div className="login-form">
        <h2>Login</h2>
        <form onSubmit={handleLogin}>
          <div className="input-group">
            <input
              type="email"
              placeholder="Email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <div className="input-group">
            <input
              type="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <button type="submit" className="btn-primary">
            Login
          </button>
        </form>
        {error && <p className="error-message">{error}</p>}
        <div className="footer">
          <p>Don't have an account?</p>
          <a href="/register">Register here</a>
        </div>
      </div>
    </div>
  );
};

export default Login;
