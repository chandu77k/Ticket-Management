import React, { useState, useEffect } from "react";
import './App.css';
import { useNavigate } from "react-router-dom";
import axios from "axios";

const TicketTable = ({ onEdit }) => {
    const [data, setData] = useState([]);
    const [user, setUser] = useState(null);
    const navigate = useNavigate();
    useEffect(() => {
        const loggedInUser = JSON.parse(localStorage.getItem('user'));
        if (loggedInUser) {
            setUser(loggedInUser);
        } else {
            navigate('/login');
            return;
        }
        axios.get('http://localhost:8080/tickets')
            .then((response) => {
                console.log('Data:', response.data);
                const isAdmin = loggedInUser.userName === 'admin' || 
                                loggedInUser.roles?.some(role => role.role === 'admin');
                if (isAdmin) {
                    setData(response.data);
                } else {
                    const filteredData = response.data.filter(ticket => 
                        ticket.createdBy.name === loggedInUser.name || 
                        ticket.assignedTo.name === loggedInUser.name
                    );
                    setData(filteredData);
                }
            })
            .catch((error) => {
                console.error('Error fetching data:', error);
            });
    }, []);
    
    const handleEdit = (ticket) => {
        onEdit(ticket);
        navigate('/addTicket');
    };
    const handleNewTicket = () => {
        navigate('/addTicket');
    };
    const handleUsers = () => {
        navigate('/userTable');
    };
    const handleDelete = (ticketId) => {
        const confirm = window.confirm("Are you sure you want to delete?");
        if (!confirm) return;
        axios.delete(`http://localhost:8080/tickets/delete/${ticketId}`)
            .then((response) => {
                setData((prevData) => prevData.filter((ticket) => ticket.id !== ticketId));
            }).catch((error) => {
                console.error("Error : ", error);
            });
    };
    const handleLogout=()=>{
        navigate('/')
    }
    const isAdmin = user?.userName === 'admin' || user?.roles?.some(role => role.role === 'admin');
    return (
        <>
        <button onClick={handleLogout}>Logout</button>
        {isAdmin && (
            <button onClick={handleUsers}>Users Data</button>
        )}
        <button onClick={handleNewTicket}>Add New Ticket</button>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Description</th>
                <th>Priority</th>
                <th>Status</th>
                <th>Created By</th>
                <th>Assigned To</th>
                <th>Ticket Comment</th>
                <th>Created Date</th>
                {isAdmin && (
                <>
                <th>Modified Date</th>
                <th>Delete Date</th>
                <th>Deleted</th>
                </>
                )}   
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            {data.map((item) => {
                return (
                    <tr key={item.id}>
                        <td>{item.id}</td>
                        <td>{item.title}</td>
                        <td>{item.description}</td>
                        <td>{item.priority.description}</td>
                        <td>{item.status.description}</td>
                        <td>{item.createdBy.name}</td>
                        <td>{item.assignedTo.name}</td>
                        <td>{item.ticketComment}</td>
                        <td>{item.createdDate}</td>
                        {isAdmin && (
                            <>
                            <td>{item.modifiedDate}</td>
                            <td>{item.deleteDate}</td>
                            <td>{item.deleted ? "Yes" : "No"}</td>
                            </>
                        )}                               
                        <td>
                            <button onClick={() => handleEdit(item)}>Edit</button>
                            <button onClick={() => handleDelete(item.id)}>Delete</button>
                        </td>
                    </tr>
                );
            })}
            </tbody>
        </table>
        </>
    );
};

export default TicketTable;
