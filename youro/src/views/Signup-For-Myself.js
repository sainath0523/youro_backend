import React,{useState} from "react";
import "../styles/Signup-For-Myself.css"

const Signupformyself= () =>
{

  const [firstname, setfirstname] = useState('');
  const [lastname, setlastname] = useState('');
  const [email, setEmail] = useState('');
  const [createpassword, setpassword] = useState('');
  const [retypepassword, setretypepassword] = useState('');
   
  // Function to handle input changes and update the state
  const handleFirstNameChange = (event) => {
    setfirstname(event.target.value);
  };
  
   const handleLastNameChange = (event) => {
    setlastname(event.target.value);
  };

   const handleEmailChange = (event) => {
    setEmail(event.target.value);
  };


 const handlePasswordChange = (event) => {
    setpassword(event.target.value);
  };

 const handleRetypePasswordChange = (event) => {
    setretypepassword(event.target.value);
  };
   
   return (
        <div>
           <div class="Signupmyself-container">
             <h1>youro</h1>
             <h4>Membership/Sign-up gives you direct access to our team of<br/> urological providers with same or next day appointments.  </h4>
             <form className="form-container">
               <div className="name-fields">
               <div className="firstname-container">
                 <label >First Name</label>
                 <input id="input-field1" type="text" value={firstname} onChange={handleFirstNameChange} ></input>
	         </div>
                 <div className="lastname-container">
                 <label >Last Name</label>
                 <input id="input-field1" type="text" value={lastname} onChange={handleLastNameChange} ></input>
                 </div>
                 </div>

                 <div className="email-fields">
                    <label>Email</label>
                <input id="input-field2" type="email" value={email} onChange={handleEmailChange} />
                 </div>

                <div className="name-fields">
               <div className="Password-container">
                 <label >Create Password</label>
                 <input id="input-field3" type="text" value={createpassword} onChange={handlePasswordChange} ></input>
	         </div>
                 <div className="retype-container">
                 <label >Re-type Password</label>
                 <input id="input-field3" type="password" value={retypepassword} onChange={handleRetypePasswordChange} ></input>
                 </div>
                 </div>
                  
                 <div className="button-container">
                   <button className="next-button">Next</button>
                </div>

              </form>
        </div>
        </div>
    )
}

export default Signupformyself;