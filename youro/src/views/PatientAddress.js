import React,{useEffect, useState} from "react";
import "../styles/PatientAdress.css";
import { useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';

const Patientaddress= (props) =>
{

  // console.log(typeof(props));
  const receivedProps = props;
  const navigate = useNavigate();
  const {
    register,
    handleSubmit,
    watch,
    formState: { errors },
  } = useForm();
  

  const onsubmit = (values) =>
  {
    const data = Object.assign(values, props.data)
    console.log("All data:",data);
   }

   
   return (
           <div class="PatientAddress-container">
            
             <h1>youro</h1>
             <p className="h3">If in-persons visits are best for you we will seamlessly<br/> transition your care to one of our urology patners in<br/>your area</p>
               <div className="Form-address-Container">
                     <div className="address-fields">
                          <div className="label-address">
                               <label>Address</label>
                               <input placeholder="Address Line 1" className="input-field input-border" type="text" {...register("Address", {
                                  required: true,
                                  maxLength: 50,
                                })} />
                                {errors?.Address?.type === "required" && <p className="error-text">This field is required</p>}
                                {errors?.Address?.type === "maxLength" && <p className="error-text">Address cannot exceed 40 characters</p>}
                          </div>

    		     <div className="label-address">
                        <input placeholder="City" className="input-field input-border" type="text" {...register("City", {
                                  required: true,
                                  maxLength: 32,
                                })}/>
                                {errors?.City?.type === "required" && <p className="error-text">This field is required</p>}
                                {errors?.City?.type === "maxLength" && <p className="error-text">Last Name cannot exceed 32 characters</p>}
                     </div>

                       <div className="label-address">
                        <input placeholder="State" className="input-field input-border" type="text" {...register("state", {
                                  required: true,
                                  maxLength: 32,
                                })} />
                                {errors?.state?.type === "required" && <p className="error-text">This field is required</p>}
                                {errors?.state?.type === "maxLength" && <p className="error-text">Email cannot exceed 32 characters</p>}
                    </div>

                    <div className="label-address">
                        <input placeholder="Pincode" className="input-field input-border" type="text" {...register("pincode", {
                                  required: true,
                                  maxLength: 32,
                                })} />
                                {errors?.pincode?.type === "required" && <p className="error-text">This field is required</p>}
                                {errors?.pincode?.type === "maxLength" && <p className="error-text">Email cannot exceed 32 characters</p>}
                    </div>

                    <p></p>
                  <div className="label-address">
                       <label>Date of Birth</label>
                        <input placeholder="MM/DD/YYY" className="input-field input-border" type="text" {...register("dob", {
                                  required: true,
                                  maxLength: 32,
                                })} />
                                {errors?.dob?.type === "required" && <p className="error-text">This field is required</p>}
                                {errors?.dob?.type === "maxLength" && <p className="error-text">Email cannot exceed 32 characters</p>}
                        
                   </div>

                      <p></p>
                     <div className="address-fields">
                       <div className="label-address">
                       <label>Legal Sex</label>
                       <p>For billing purpose ,which sex does your insurance record has?</p>
                       <div>
                       <input {...register("sex",{required:true})} type="radio" value="male" />male
                      </div>
			<div>
			<input {...register("sex",{required:true})} type="radio" value="female" />female
			</div>
                        {errors?.sex?.type ==="required" && <p className="error-text">This field is required</p>}
                      </div>
		      </div>
                    

              
                
                {/* <div className="bottom-fields">
                   <p>Already youro member? <span>Login</span></p>      
		</div>
                
		 <div className="button-container">
                   <button className="next-button">Next</button>
                </div> */}
        </div>
        
        </div>
        <div style={{width: "50%", margin: "0px auto 10px auto", paddingTop: "0px"}}>
        <div className="required-fields" style={{justifyContent: 'flex-between'}}>
                    <div className="myself-label" style={{width: "48%", marginTop: '0px'}}>
                     <p>Already youro member? <span onClick={() => navigate('/login')} className="color-secondary" style={{cursor: 'pointer'}}><u>Login</u></span> </p>
	            </div>
                    <div className="myself-label" style={{width: "45%", position: "relative"}}>
                        <div className="next-button btn-filled" onClick={handleSubmit((onsubmit))}>Next</div>
                        
                    </div>
                </div>
        </div>
       </div>
    )
}

export default Patientaddress;
