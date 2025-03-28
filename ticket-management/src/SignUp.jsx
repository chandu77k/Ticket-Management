import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
const SignUp =() => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        userName: "",
        password: "",
        name: "",
        email: "",
        phoneNumber: "",
        department: "",
        role: ""
    });
    const handleChange = (event) => {
        setFormData({
            ...formData,
            [event.target.name]: event.target.value, 
        });
    };
    const handleRoleChange = (event) =>{
        const { value, checked } = event.target;
        if (checked) {
            setFormData((prevFormData) => ({
                ...prevFormData,
                role: value
            }));
        }else{
            setFormData((prevFormData)=> ({
                ...prevFormData,
                role:""
            }));
        }
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        const userWithRole ={
            userName: formData.userName,
            password: formData.password,
            name: formData.name,
            email: formData.email,
            phoneNumber: formData.phoneNumber,
            department: formData.department,
            role: formData.role
        };
        try {
            const response = await axios.post("http://localhost:8080/users/add", userWithRole);
            console.log(response.data);
            alert("Successfully Registered");
            navigate('/');
        }catch(error){
            console.error("Error : ", error);
            alert("Registration Failed");
        }
    };
    return(
        <>
        <h2>Sign Up</h2>
        <form onSubmit={handleSubmit}>
            <label htmlFor="userNames">UserName : </label>
            <input type="text" name="userName" value={formData.userName} onChange={handleChange}/><br />
            <label htmlFor="passwords">Password : </label>
            <input type="password" name="password" value={formData.password} onChange={handleChange}/><br />
            <label htmlFor="names">Name : </label>
            <input type="text" name="name" value={formData.name} onChange={handleChange}/><br />
            <label htmlFor="emails">Email : </label>
            <input type="email" pattern=".+questk2.com" name="email" value={formData.email} onChange={handleChange}/><br />
            <label htmlFor="phones">Mobile Number : </label>
            <input type="text" name="phoneNumber" value={formData.phoneNumber} onChange={handleChange}/><br />
            <label htmlFor="departments">Department : </label>
            <input type="text" name="department" value={formData.department} onChange={handleChange}/><br />
            <label htmlFor="roles">Role : </label><br />
            {/* <input type="checkbox" name="role" value="Admin" checked={formData.role === "Admin"} onChange={handleRoleChange}/>
            <label htmlFor="admins">Admin</label><br /> */}
            <input type="checkbox" name="role" value="Frontend Developer" checked={formData.role === "Frontend Developer"} onChange={handleRoleChange}/>
            <label htmlFor="admins">Frontend Developer</label><br />
            <input type="checkbox" name="role" value="Backend Developer" checked={formData.role === "Backend Developer"} onChange={handleRoleChange}/>
            <label htmlFor="admins">Backend Developer</label><br />
            <button type="submit">Register</button>
        </form>
        </>
    );
}
export default SignUp;