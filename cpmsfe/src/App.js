// import logo from "./logo.svg";
import { Layout } from "antd";
import "./App.css";

const { Header, Content } = Layout;

function App() {
  return (
    <Layout>
      <Header>
        <div style={{ fontSize: 16, fontWeight: 600, color: "white" }}>
          Community Property Management System
        </div>
      </Header>
      <Content></Content>
    </Layout>
  );
}

export default App;
