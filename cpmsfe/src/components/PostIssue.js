const PostIssue = () => {
  const [description, setDescription] = useState('');

  const handlePostIssue = () => {  };

  return (
    <div>
      <h2>Post a New Issue</h2>
      <textarea
        placeholder="Describe the issue"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
      />
      <button onClick={handlePostIssue}>Submit</button>
    </div>
  );
};

export default PostIssue;
