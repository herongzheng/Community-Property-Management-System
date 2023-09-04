const ResidentIssuesList = ({ username }) => {
  const [issues, setIssues] = useState([]);

  useEffect(() => {  }, [username]);

  return (
    <div>
      <h2>Your Issues</h2>
      <ul>
        {issues.map((issue) => (
          <li key={issue.id}>{issue.description}</li>
        ))}
      </ul>
    </div>
  );
};

export default ResidentIssuesList;