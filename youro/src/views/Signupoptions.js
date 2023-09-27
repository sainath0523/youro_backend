import React from "react";
import "../styles/login.css"
import { Link } from "react-router-dom";

const Signupoptions= () => {
    return (
        <div>
           <div class="option-select">
           <h1>youro</h1>
           <h2>Who is this membership for? </h2>
           <p>Membership gives you or your family access <br/> to a urology-trained provider when you need it.</p>
           <button className="options-button-1" >For Myself </button>
           <button className="options-button-2"> For A Family Member</button> 
           </div>
        </div>
    )
}

export default Signupoptions;