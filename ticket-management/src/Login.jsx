import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { Link } from "react-router-dom";
import SignUp from './SignUp'
const Login = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();
    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.get('http://localhost:8080/users');
            const users = response.data;
            const user = users.find(user => user.userName === username && user.password === password);
            if (user) {
                localStorage.setItem('user', JSON.stringify(user));
                navigate('/ticketTable');
            } else {
                setError('Invalid credentials. Please try again.');
            }
        } catch (error) {
            setError('Error fetching users. Please try again later.');
            console.error("Login error: ", error);
        }
    };
    return (
        <div>
            <h2>Login</h2>
            <form onSubmit={handleLogin}>
                <label htmlFor="userName">User Name : </label>
                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                /><br/>
                <label htmlFor="password">Password : </label>
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                /><br/>
                <button type="submit">Login</button>
            </form>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <h3>Doesn't have an account <Link to="/signup">Register here...</Link></h3>
        </div>
    );
};

export default Login;
