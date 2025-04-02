import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

const NewTicket = ({ selectedTicket, setSelectedTicket }) => {
  const navigate = useNavigate();

  const [loggedInUser, setLoggedInUser] = useState(null);
  const [allUsers, setAllUsers] = useState([]);
  const [ticketPriority, setTicketPriority] = useState([]);
  const [ticketStatus, setTicketStatus] = useState([]);
  const [ticketData, setTicketData] = useState({
    id: null,
    title: "",
    description: "",
    priority: "",
    status: "",
    createdBy: "",
    assignedTo: "",
    ticketComment: "",
  });

  useEffect(() => {
    const user = JSON.parse(localStorage.getItem("user"));
    if (user) {
      setLoggedInUser(user);
      setTicketData((prevData) => ({
        ...prevData,
        createdBy: user.id,
      }));
    }

    if (selectedTicket) {
      setTicketData((prevData) => ({
        ...prevData,
        ...selectedTicket,
        assignedTo:
          selectedTicket.assignedTo?.id || selectedTicket.assignedTo || "",
        priority: selectedTicket.priority?.id || selectedTicket.priority || "",
        status: selectedTicket.status?.id || selectedTicket.status || "",
      }));
    }
    axios.get("http://localhost:8080/ticketPriority").then((response) => {
      const priorities = response.data.map((priority) => ({
        id: priority.id,
        description: priority.description,
      }));
      setTicketPriority(priorities);
    });

    axios.get("http://localhost:8080/ticketStatus").then((response) => {
      const statuses = response.data.map((status) => ({
        id: status.id,
        description: status.description,
      }));
      setTicketStatus(statuses);
    });

    axios
      .get("http://localhost:8080/users")
      .then((response) => {
        setAllUsers(response.data);
      })
      .catch((error) => console.error("Error fetching users:", error));
  }, [selectedTicket]);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setTicketData((prevData) => ({
      ...prevData,
      [name]:
        name === "priority" ||
        name === "status" ||
        name === "createdBy" ||
        name === "assignedTo"
          ? parseInt(value)
          : value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    const ticketDetails = {
      title: ticketData.title,
      description: ticketData.description,
      priority: ticketData.priority,
      status: ticketData.status,
      createdBy: loggedInUser.id,
      assignedTo: ticketData.assignedTo,
      ticketComment: ticketData.ticketComment,
    };

    try {
      if (ticketData.id) {
        await axios.put(
          `http://localhost:8080/tickets/update/${ticketData.id}`,
          ticketDetails
        );
        alert("Ticket updated successfully");
      } else {
        await axios.post("http://localhost:8080/tickets/add", ticketDetails);
        alert("Ticket added successfully");
      }
      navigate("/ticketTable");
    } catch (error) {
      console.error("Error:", error);
      alert("Ticket operation failed");
    }
  };

  const handleLogout = () => {
    navigate("/");
  };

  return (
    <>
      <div id="container">
        <div className="form-container">
          <Button variant="secondary" onClick={handleLogout}>
            Logout
          </Button>
          <h2>Ticket Form</h2>

          <Form onSubmit={ticketData.id ? handleSubmit : handleSubmit}>
            <Form.Label htmlFor="title">Title</Form.Label>
            <Form.Control
              type="text"
              name="title"
              value={ticketData.title}
              onChange={handleChange}
              required
            />

            <Form.Label htmlFor="description">Description</Form.Label>
            <Form.Control
              type="text"
              name="description"
              value={ticketData.description}
              onChange={handleChange}
              required
            />

            <Form.Label htmlFor="priority">Priority</Form.Label>
            <Form.Select
              name="priority"
              value={ticketData.priority || ""}
              onChange={handleChange}
              required
            >
              <option value="">None</option>
              {ticketPriority.map((priority) => (
                <option key={priority.id} value={priority.id}>
                  {priority.description}
                </option>
              ))}
            </Form.Select>

            <Form.Label htmlFor="status">Status</Form.Label>
            <Form.Select
              name="status"
              value={ticketData.status || ""}
              onChange={handleChange}
              required
            >
              <option value="">None</option>
              {ticketStatus.map((status) => (
                <option key={status.id} value={status.id}>
                  {status.description}
                </option>
              ))}
            </Form.Select>

            <Form.Label>Created By</Form.Label>
            <Form.Control
              type="text"
              value={loggedInUser?.name || "Loading..."}
              readOnly
            />

            <Form.Label htmlFor="assigned">Assigned To</Form.Label>
            <Form.Select
              name="assignedTo"
              value={ticketData.assignedTo || ""}
              onChange={handleChange}
              required
            >
              <option value="">None</option>
              {allUsers.map((user) => (
                <option key={user.id} value={user.id}>
                  {user.name}
                </option>
              ))}
            </Form.Select>

            <Form.Label htmlFor="ticketComment">Ticket Comment</Form.Label>
            <Form.Control
              type="text"
              name="ticketComment"
              value={ticketData.ticketComment}
              onChange={handleChange}
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

export default NewTicket;
