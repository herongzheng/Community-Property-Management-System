const HostResidentIssuesList = ({ residentId }) => {
  const [issues, setIssues] = useState([]);

  useEffect(() => {  }, [residentId]);

  return (
    <div>
      <h2>Resident's Issues</h2>
      <ul>
        {issues.map((issue) => (
          <li key={issue.id}>{issue.description}</li>
        ))}
      </ul>
    </div>
  );
};

export default HostResidentIssuesList;