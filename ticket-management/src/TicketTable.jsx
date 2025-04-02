import React, { useState, useEffect } from "react";
import "./App.css";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Table from "react-bootstrap/Table";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";

const TicketTable = ({ onEdit }) => {
  const [data, setData] = useState([]);
  const [user, setUser] = useState(null);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [ticketToDelete, setTicketToDelete] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const loggedInUser = JSON.parse(localStorage.getItem("user"));
    if (!loggedInUser) {
      navigate("/login");
      return;
    }

    setUser(loggedInUser);

    axios
      .get(`http://localhost:8080/tickets/${loggedInUser.id}`)
      .then((response) => {
        console.log("API Response:", response.data);
        setData(response.data);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
  }, [navigate]);

  const handleEdit = (ticket) => {
    onEdit(ticket);
    navigate("/addTicket");
  };

  const handleNewTicket = () => {
    navigate("/addTicket");
  };

  const handleUsers = () => {
    navigate("/userTable");
  };

  const handleDelete = (ticket) => {
    setTicketToDelete(ticket);
    setShowDeleteModal(true);
  };

  const handleLogout = () => {
    navigate("/");
  };

  const confirmDelete = () => {
    const ticketDTO = {
      id: ticketToDelete.id,
      isDeleted: true,
      deleteDate: new Date().toISOString(),
      priority: ticketToDelete.priority.id,
      status: ticketToDelete.status.id,
      createdBy: ticketToDelete.createdBy.id,
      assignedTo: ticketToDelete.assignedTo.id,
      ticketComment: ticketToDelete.ticketComment,
    };

    axios
      .put(
        `http://localhost:8080/tickets/delete/${ticketToDelete.id}`,
        ticketDTO
      )
      .then((response) => {
        console.log("Ticket deleted:", response.data);
        alert("Ticket deleted successfully");
        setData((prevData) =>
          prevData.filter((t) => t.id !== ticketToDelete.id)
        );
        setShowDeleteModal(false);
      })
      .catch((error) => {
        console.error("Error deleting ticket:", error);
        alert("Failed to delete the ticket");
        setShowDeleteModal(false);
      });
  };

  const handleCloseModal = () => {
    setShowDeleteModal(false);
    setTicketToDelete(null);
  };

  const isAdmin =
    user?.userName === "admin" ||
    user?.roles?.some((role) => role.role === "admin");
  const filteredData = isAdmin
    ? data
    : data.filter((ticket) => !ticket.deleted);

  return (
    <>
      <div className="ticket-container">
        <Button
          variant="secondary"
          onClick={handleLogout}
          className="ticket-logout-button"
        >
          Logout
        </Button>
        {isAdmin && (
          <Button
            variant="info"
            onClick={handleUsers}
            className="ticket-users-button"
          >
            Users Data
          </Button>
        )}
        <Button
          variant="primary"
          onClick={handleNewTicket}
          className="ticket-new-button"
        >
          Add New Ticket
        </Button>

        <Table striped bordered hover className="ticket-table">
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
              <th>Modified Date</th>
              {isAdmin && (
                <>
                  <th>Delete Date</th>
                  <th>Deleted</th>
                </>
              )}
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {filteredData.map((item) => (
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
                <td>{item.modifiedDate}</td>
                {isAdmin && (
                  <>
                    <td>{item.deleteDate}</td>
                    <td>{item.deleted ? "Yes" : "No"}</td>
                  </>
                )}
                <td>
                  <Button
                    variant="outline-primary"
                    onClick={() => handleEdit(item)}
                    disabled={item.deleted}
                  >
                    Edit
                  </Button>
                  <Button
                    variant="outline-danger"
                    onClick={() => handleDelete(item)}
                    disabled={item.deleted}
                  >
                    Delete
                  </Button>
                </td>
              </tr>
            ))}
          </tbody>
        </Table>

        <Modal
          show={showDeleteModal}
          onHide={handleCloseModal}
          className="ticket-modal"
        >
          <Modal.Header closeButton>
            <Modal.Title>Confirm Deletion</Modal.Title>
          </Modal.Header>
          <Modal.Body>Are you sure you want to delete this ticket?</Modal.Body>
          <Modal.Footer>
            <Button
              variant="secondary"
              onClick={handleCloseModal}
              className="ticket-modal-close-button"
            >
              Cancel
            </Button>
            <Button
              variant="danger"
              onClick={confirmDelete}
              className="ticket-modal-confirm-button"
            >
              Confirm Delete
            </Button>
          </Modal.Footer>
        </Modal>
      </div>
    </>
  );
};

export default TicketTable;
