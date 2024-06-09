import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom'
import axios from 'axios';
import './ResetPassword.css';

const ResetPassword = () => {
    const [username, setUsername] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const navigate = useNavigate();
    const AUTHORIZATION_TOKEN = process.env.REACT_APP_AUTHORIZATION_TOKEN;
    const HOST= process.env.REACT_APP_HOST;
    const PORT= process.env.REACT_APP_PORT;
    const BASE_PATH= process.env.REACT_APP_AUTH_BASE_PATH;

    //Reset password
    const resetPassword = async () => {
      try {
        if (newPassword !== confirmPassword) {
          alert('Passwords do not match. Please try again.');
          return;
        }

        const response = await axios.post(`http://${HOST}:${PORT}${BASE_PATH}/resetPassword`, { username, password: newPassword }, {
          headers: {
          'Content-Type': 'application/json',
          'Authorization': `${AUTHORIZATION_TOKEN}`
          }
        });

        if (response.status === 200) {
          if (response.data.returnCode === '0') {
            alert(response.data.returnMessage);
            navigate('/'); // Redirect to login page using history
          } else {
            throw new Error('Password Reset Failed: ' + response.data.serviceResult.returnMessage);
          }

          //In case of a connection error to the database
        } else {
          throw new Error('Failed to reset password. Please try again later.');
        }

        //In case of a connection error to the api
      } catch (error) {
        console.error('Password Reset Error:', error);
        alert(error.message);
      }
    };

    const handleSubmit = async (event) => {
      event.preventDefault();
      await resetPassword();
    };

    return (
      <div className="reset-password-container">
        <form id="resetPasswordForm" onSubmit={handleSubmit}>
          <h1>Reset Password</h1>
          <label htmlFor="username">Username:</label>
          <input
            type="text"
            id="username"
            name="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
          <label htmlFor="newPassword">New Password:</label>
          <input
            type="password"
            id="newPassword"
            name="newPassword"
            value={newPassword}
            onChange={(e) => setNewPassword(e.target.value)}
            required
          />
          <label htmlFor="confirmPassword">Confirm Password:</label>
          <input
            type="password"
            id="confirmPassword"
            name="confirmPassword"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            required
          />
          <button type="submit">Reset Password</button>
          <Link to="/" className="goBackButton">Go back to Login</Link>
          <footer>&copy; Bar Yaron & Yakir Zafrani</footer>
        </form>
      </div>
    );
};

export default ResetPassword;
