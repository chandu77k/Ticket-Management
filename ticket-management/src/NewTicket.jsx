import React, {useState,useEffect} from "react"
import axios from "axios"
import { useNavigate } from "react-router-dom";
const NewTicket = ({selectedTicket, setSelectedTicket}) => {
    const navigate = useNavigate();
    const [userName,setUserName] = useState([]);
    const [ticketPriority,setTicketPriority] = useState([]);
    const [ticketStatus,setTicketStatus] = useState([]);
    const [ticketData, setTicketData] = useState({
        id: null,
        title: "",
        description:"",
        priority: "",
        status: "",
        createdBy: "",
        assignedTo: "",
        ticketComment: ""
    });
    useEffect(()=> {
        if(selectedTicket){
            setTicketData(selectedTicket);
        }
        axios.get('http://localhost:8080/users')
        .then((response)=>{
            if(response && response.data){
                const names = response.data.map((user)=>(
                    {
                      id: user.id,
                      name: user.name,
                    }));
                setUserName(names);
                console.log(names);
            }  
        });
        axios.get('http://localhost:8080/ticketPriority')
        .then((response)=>{
            if(response && response.data){
            const priorities = response.data.map((ticketPriority)=> ({
                id: ticketPriority.id,
                description: ticketPriority.description,
            }));
            setTicketPriority(priorities);
            console.log(priorities);
            }
        });
        axios.get('http://localhost:8080/ticketStatus')
        .then((response)=>{
            if(response && response.data){
                const statuss = response.data.map((ticketStatus)=> ({
                    id: ticketStatus.id,
                    description: ticketStatus.description,
                }));
                setTicketStatus(statuss);
                console.log(statuss);
            }
        });
    },[selectedTicket]);

    const handleChange = (event) => {
        console.log(event.target.value);
        setTicketData({
            ...ticketData,
            [event.target.name]: event.target.value,
        });
    };

    const handleUpdate =async(event)=> {
        event.preventDefault();
        const ticketDetails = {
            title: ticketData.title,
            description: ticketData.description,
            priority: ticketData.priority,
            status: ticketData.status,
            createdBy: ticketData.createdBy,
            assignedTo: ticketData.assignedTo,
            ticketComment: ticketData.ticketComment
        };
        try{
            const response = await axios.put(`http://localhost:8080/tickets/update/${ticketData.id}`, ticketDetails);
            console.log(response.data)
            alert("Ticket Upadted successfully");
            setSelectedTicket(null);
            navigate('/ticketTable');
        }catch(error){
            console.error("Error : ",error);
            alert("Ticket update failed");
        }
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        const ticketDetails ={
            title: ticketData.title,
            description: ticketData.description,
            priority: ticketData.priority,
            status: ticketData.status,
            createdBy: ticketData.createdBy,
            assignedTo: ticketData.assignedTo,
            ticketComment: ticketData.ticketComment
        };
        try{
            const response = await axios.post('http://localhost:8080/tickets/add',ticketDetails);
            console.log(response.data);
            alert("Ticket added successfully");
            navigate('/ticketTable');
        }catch(error){
            console.error("Error : ",error);
            alert("Ticket adding failed");
        }
    };
    const handleLogout=()=>{
        navigate('/');
    }
    return(
        <>
        <button onClick={handleLogout}>Logout</button>
        <h2>Ticket Form</h2>
        <form onSubmit={ticketData.id ? handleUpdate : handleSubmit}>
            <label htmlFor="titles">Title : </label>
            <input type="text" name="title" value={ticketData.title} onChange={handleChange}/><br />
            <label htmlFor="descriptions">Description : </label>
            <input type="text" name="description" value={ticketData.description} onChange={handleChange}/><br />
            <label htmlFor="priorities">Priority : </label>
            <select name="priority" value={ticketData.priority} onChange={handleChange}>
                <option value="">None</option>
                {ticketPriority.map((priority)=>(
                    <option key={priority.id} value={priority.id}>{priority.description}</option>
                ))}
            </select>
            <br />
            <label htmlFor="statuss">Status :</label>
            <select name="status" value={ticketData.status} onChange={handleChange}>
                <option value="">None</option>
                {ticketStatus.map((status)=>(
                    <option key={status.id} value={status.id}>{status.description}</option>
                ))}
            </select>
            <br />
            <label htmlFor="created">Created By : </label>
            <select name="createdBy" value={ticketData.createdBy} onChange={handleChange}>
                <option value="">None</option>
            {userName.map((user) => (
                <option key={user.id} value={user.id}>{user.name}</option>
            ))}
            </select><br />
            
            <label htmlFor="assigned">Assigned To : </label>
            <select name="assignedTo" value={ticketData.assignedTo} onChange={handleChange}>
                <option value="">None</option>
            {userName.map((user) => (
                <option key={user.id} value={user.id}>{user.name}</option>
            ))}
            </select><br />
            <label htmlFor="ticketComment">Ticket Comment : </label>
            <input type="text" name="ticketComment" value={ticketData.ticketComment} onChange={handleChange}/><br />
            <button type="submit">Submit</button>
        </form>
        </>
    );
}
export default NewTicket;