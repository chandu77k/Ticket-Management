import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
const SignUp = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    userName: "",
    password: "",
    name: "",
    email: "",
    phoneNumber: "",
    department: "",
    role: null,
  });
  const handleChange = (event) => {
    setFormData({
      ...formData,
      [event.target.name]: event.target.value,
    });
  };
  const handleRoleChange = (event) => {
    const { value, checked } = event.target;
    if (checked) {
      setFormData((prevFormData) => ({
        ...prevFormData,
        role: parseInt(value, 10),
      }));
    } else {
      setFormData((prevFormData) => ({
        ...prevFormData,
        role: null,
      }));
    }
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    const userWithRole = {
      userName: formData.userName,
      password: formData.password,
      name: formData.name,
      email: formData.email,
      phoneNumber: formData.phoneNumber,
      department: formData.department,
      role: formData.role,
    };
    try {
      const response = await axios.post(
        "http://localhost:8080/users/add",
        userWithRole
      );
      console.log(response.data);
      alert("Successfully Registered");
      navigate("/");
    } catch (error) {
      console.error("Error : ", error);
      alert("Registration Failed");
    }
  };
  return (
    <>
      <div id="container">
        <div className="form-container">
          <h2>Sign Up</h2>
          <Form onSubmit={handleSubmit}>
            <Form.Label htmlFor="userNames">UserName</Form.Label>
            <Form.Control
              type="text"
              name="userName"
              value={formData.userName}
              onChange={handleChange}
              placeholder="Enter Username"
              required
            />

            <Form.Label htmlFor="passwords">Password</Form.Label>
            <Form.Control
              type="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              placeholder="Enter Password"
              required
            />

            <Form.Label htmlFor="names">Name</Form.Label>
            <Form.Control
              type="text"
              name="name"
              value={formData.name}
              onChange={handleChange}
              placeholder="Enter Name"
              required
            />

            <Form.Label htmlFor="emails">Email</Form.Label>
            <Form.Control
              type="email"
              pattern=".+questk2.com"
              name="email"
              value={formData.email}
              onChange={handleChange}
              placeholder="Enter Email"
              required
            />

            <Form.Label htmlFor="phones">Mobile Number</Form.Label>
            <Form.Control
              type="text"
              name="phoneNumber"
              value={formData.phoneNumber}
              onChange={handleChange}
              placeholder="Enter Phone Number"
              required
            />

            <Form.Label htmlFor="departments">Department</Form.Label>
            <Form.Control
              type="text"
              name="department"
              value={formData.department}
              onChange={handleChange}
              placeholder="Enter Department"
              required
            />

            <Form.Label htmlFor="roles">Role</Form.Label>
            <Form.Check
              name="role"
              value="1"
              checked={formData.role === 1}
              onChange={handleRoleChange}
              label="Frontend Developer"
            />
            <Form.Check
              name="role"
              value="2"
              checked={formData.role === 2}
              onChange={handleRoleChange}
              label="Backend Developer"
            />

            <Button variant="success" type="submit">
              Submit
            </Button>
          </Form>
        </div>
      </div>
    </>
  );
};
export default SignUp;
