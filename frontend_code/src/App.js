/**
 * Copyright (c) 2023
 *
 * @summary Implementation of App.js
 * @author Zilin Li
 * @date 2023-06-28
 *
 */

// Project imports
import LoginPage from "./components/common/LoginPage";
import background from "./images/background_image.jpg";
import HostHomePage from "./components/host/HostHomePage";
import GuestHomePage from "./components/guest/GuestHomePage";

// Antd imports
import { UserOutlined } from "@ant-design/icons";
import { Layout, Dropdown, Menu, Button, Row } from "antd";

// React imports
import React from "react";

const { Header, Content } = Layout;

class App extends React.Component {
    
    state = {
      authed: false,
      asHost: false,
    };

    componentDidMount() {
      const authToken = localStorage.getItem("authToken");
      const asHost = localStorage.getItem("asHost") === "true";
      this.setState({
        authed: authToken !== null,
        asHost,
      });
    }

    handleLoginSuccess = (token, asHost) => {
      localStorage.setItem("authToken", token);
      localStorage.setItem("asHost", asHost);
      this.setState({
        authed: true,
        asHost,
      });
    };

    handleLogOut = () => {
      localStorage.removeItem("authToken");
      localStorage.removeItem("asHost");
      this.setState({
        authed: false,
      });
    };

    renderContent = () => {

      if (!this.state.authed) {
        return <LoginPage handleLoginSuccess={this.handleLoginSuccess} />;
      }

      if (this.state.asHost) {
        return <HostHomePage />;
      }
      return (
          <GuestHomePage />
        );
    };

    // Logout menu
    userMenu = (
      <Menu>
        <Menu.Item key="logout" onClick={this.handleLogOut}>
          Log Out
        </Menu.Item>
      </Menu>
    );

    render() {
      return (
        <Layout style={{ height: "100vh", backgroundSize:"cover", backgroundImage:`url(${background})`}}>
          <Header>
            <div 
              style={{ fontSize: 16, fontWeight: 600, color: "white" }}
            >
              Stays Booking
            </div>
            {this.state.authed && (
              <div>
                <Dropdown trigger="click" overlay={this.userMenu}>
                  <Button icon={<UserOutlined />} shape="circle" />
                </Dropdown>
              </div>
            )}
          </Header>
          <Content
            style={{ height: "calc(100% - 64px)", margin: 20, overflow: "auto"}}
          >
            {this.renderContent()}
          </Content>
        </Layout>
      );
    }
}


export default App;