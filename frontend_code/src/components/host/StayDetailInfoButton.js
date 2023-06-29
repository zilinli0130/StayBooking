/**
 * Copyright (c) 2023
 *
 * @summary Implementation of StayDetailsButton.js
 * @author Zilin Li
 * @date 2023-06-28
 *
 */

// Antd imports
import { Button, Tooltip, Space, Modal } from "antd";
import { InfoCircleOutlined } from "@ant-design/icons";
import Text from "antd/lib/typography/Text";

// React imports
import React from "react";

class StayDetailInfoButton extends React.Component {
    state = {
      modalVisible: false,
    };

    openModal = () => {
      this.setState({
        modalVisible: true,
      });
    };

    handleCancel = () => {
      this.setState({
        modalVisible: false,
      });
    };

    render() {
      
      const { name, description, address, guest_number } = this.props.stay;
      return (
        <>
          <Tooltip title="View Stay Details">
            <Button
              onClick={this.openModal}
              style={{ border: "none" }}
              size="large"
              icon={<InfoCircleOutlined />}
            />
          </Tooltip>
          {this.state.modalVisible && (
            <Modal
              title={name}
              centered={true}
              visible={this.state.modalVisible}
              footer={[
                <Button key="submit" type="primary" onClick={this.handleCancel}>
                    Close
                </Button>,
              ]}
              closable={false}  
            >
              <Space direction="vertical">
                <Text strong={true}>Description</Text>
                <Text type="secondary">{description}</Text>
                <Text strong={true}>Address</Text>
                <Text type="secondary">{address}</Text>
                <Text strong={true}>Guest Number</Text>
                <Text type="secondary">{guest_number}</Text>
              </Space>
            </Modal>
          )}
        </>
      );
    }
};

export default StayDetailInfoButton;
  