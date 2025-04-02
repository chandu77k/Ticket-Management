import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import axios from "axios";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import "./App.css";
const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8080/login", {
        userName: username,
        password: password,
      });

      if (response.status === 200) {
        localStorage.setItem("user", JSON.stringify(response.data));
        navigate("/ticketTable");
      }
    } catch (error) {
      setError("Invalid credentials or server error.");
      console.error("Login error: ", error.response?.data || error.message);
    }
  };

  return (
    <>
      <div id="container">
        <div className="form-container">
          <Form onSubmit={handleLogin}>
            <Form.Group className="mb-3" controlId="formBasicEmail">
              <Form.Label>Username</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
              />
              <Form.Text className="text-muted">
                We'll never share your details with anyone else.
              </Form.Text>
            </Form.Group>

            <Form.Group className="mb-3" controlId="formBasicPassword">
              <Form.Label>Password</Form.Label>
              <Form.Control
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </Form.Group>

            <Button variant="success" type="submit">
              Submit
            </Button>
          </Form>
          {error && <p style={{ color: "red" }}>{error}</p>}
          <h6>
            Don't have an account? <Link to="/signup">Register here...</Link>
          </h6>
        </div>
      </div>
    </>
  );
};

export default Login;
