import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

import axios from 'axios';
import './Login.css';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();
  const AUTHORIZATION_TOKEN = process.env.REACT_APP_AUTHORIZATION_TOKEN;
  const HOST= process.env.REACT_APP_HOST;
  const PORT= process.env.REACT_APP_PORT;
  const BASE_PATH= process.env.REACT_APP_AUTH_BASE_PATH;
  const URL=`http://${HOST}:${PORT}${BASE_PATH}/login`

  const authenticateUser = async () => {
    try {
      console.log("Authorization Token:", AUTHORIZATION_TOKEN);
      const response = await axios.post(`http://${HOST}:${PORT}${BASE_PATH}/login`, { username, password }, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `${AUTHORIZATION_TOKEN}`
        }
      });

      if (response.status === 200) {
        if (response.data.returnCode === '0') {
          localStorage.setItem('sessionToken', 'authenticated');
          navigate('/museum-exhibition-upload'); // Navigate to the desired route
        } else {
          throw new Error(response.data.returnMessage);
        }
      } else {
        throw new Error('Authentication Failed');
      }
    } catch (error) {
      console.error('Authentication Error:', error);
      alert(error.message);
    }
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    await authenticateUser();
  };

  return (
    <div className="login-container">
      <form id="loginForm" onSubmit={handleSubmit}>
        <h1>Login</h1>
        <label htmlFor="username">Username:</label>
        <input
          type="text"
          id="username"
          name="username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
        />
        <label htmlFor="password">Password:</label>
        <input
          type="password"
          id="password"
          name="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <button type="submit">Login</button>
        <Link to="/reset-password" className="forgotPasswordButton">Forgot Password?</Link>
        <footer>&copy; Bar Yaron & Yakir Zafrani</footer>
      </form>
    </div>
  );
};

export default Login;