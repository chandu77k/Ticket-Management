import React, {useState, useEffect} from "react"
import './App.css'
import axios from "axios";
import { useNavigate } from "react-router-dom";
const UserTable = ()=>{
    const [data, setData] = useState([]);
    const navigate = useNavigate();
    useEffect(()=>{
        axios.get('http://localhost:8080/users')
        .then((response)=> {
            console.log('Data : ',response.data);
            setData(response.data);
        }).catch((error)=>{
            console.error('Error fetching data : ',error);
        });
    },[]);
    const handleTickets = ()=>{
        navigate('/ticketTable');
    };
    const handleLogout=()=>{
        navigate('/');
    };
    return(
    <>
    <button onClick={handleLogout}>Logout</button>
    <button onClick={handleTickets}>Tickets Table</button>
    <table>
        <thead>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>User Name</th>
                <th>Email</th>
                <th>Department</th>
                <th>Phone Number</th>
                <th>Role</th>
            </tr>
        </thead>
        <tbody>
            {data.map((item)=>{  
                return(   
                <>             
                <tr key={item.id}>
                <td>{item.id}</td>
                <td>{item.name}</td>
                <td>{item.userName}</td>
                <td>{item.email}</td>
                <td>{item.department}</td>
                <td>{item.phoneNumber}</td>
                <td>{item.roles?.map(role=>role.role)}</td>
                </tr> 
                </>
                );       
            })}
        </tbody>
    </table>
    </>
    );
}
export default UserTable;