function AddPackage() {
    const [packageInfo, setPackageInfo] = useState({
      firstName: '',
      lastName: '',
      });
  
    const handleInputChange = (e) => {
      const { name, value } = e.target;
      setPackageInfo({ ...packageInfo, [name]: value });
    };
  
    const handleSubmit = async (e) => {
      e.preventDefault();
      try {
        const response = await fetch('/api/add-package', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(packageInfo),
        });
      } catch (error) {
      }
    };
  
    return (
      <div>
        <h2>Add Package</h2>
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            name="firstName"
            placeholder="First Name"
            onChange={handleInputChange}
          />
          <input
            type="text"
            name="lastName"
            placeholder="Last Name"
            onChange={handleInputChange}
          />
         <button type="submit">Add Package</button>
        </form>
      </div>
    );
  }
  
  export default AddPackage;