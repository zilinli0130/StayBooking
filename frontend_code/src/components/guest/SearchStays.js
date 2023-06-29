/**
 * Copyright (c) 2023
 *
 * @summary Implementation of SearchStay.js
 * @author Zilin Li
 * @date 2023-06-28
 *
 */

// React imports
import React from "react";

// Antd imports
import {
  Image,
  message,
  List,
  Form,
  InputNumber,
  DatePicker,
  Button,
  Card,
  Carousel
} from "antd";
import Text from "antd/lib/typography/Text";
import { LeftCircleFilled, RightCircleFilled } from "@ant-design/icons";

// Project imports
import { searchStays } from "../../utils/backend_utils";
import BookStayButton from "./BookStayButton ";
import StayDetailInfoButton from "../host/StayDetailInfoButton"

class SearchStays extends React.Component {
    state = {
      data: [],
      loading: false,
    };
   
    search = async (query) => {
      this.setState({
        loading: true,
      });
  
      try {
        const resp = await searchStays(query);
        this.setState({
          data: resp,
        });
      } catch (error) {
        message.error(error.message);
      } finally {
        this.setState({
          loading: false,
        });
      }
    };
  
    render() {
      return (
        <>
          <Form onFinish={this.search} layout="inline">
            <Form.Item
              label="Guest Number"
              name="guest_number"
              rules={[{ required: true }]}
            >
              <InputNumber min={1} />
            </Form.Item>
            <Form.Item
              label="Checkin Date"
              name="checkin_date"
              rules={[{ required: true }]}
            >
              <DatePicker />
            </Form.Item>
            <Form.Item
              label="Checkout Date"
              name="checkout_date"
              rules={[{ required: true }]}
            >
              <DatePicker />
            </Form.Item>
            <Form.Item>
              <Button
                loading={this.state.loading}
                type="primary"
                htmlType="submit"
              >
                Submit
              </Button>
            </Form.Item>
          </Form>
          <List
            style={{ marginTop: 20 }}
            loading={this.state.loading}
            grid={{
              gutter: 16,
              xs: 1,
              sm: 3,
              md: 3,
              lg: 3,
              xl: 4,
              xxl: 4,
            }}
            dataSource={this.state.data}
            renderItem={(item) => (
              <List.Item>
                <Card
                  key={item.id}
                  title={
                    <div style={{ display: "flex", alignItems: "center" }}>
                      <Text ellipsis={true} style={{ maxWidth: 150 }}>
                        {item.name}
                      </Text>
                      <StayDetailInfoButton stay={item} />
                    </div>
                  }
                  extra={<BookStayButton stay={item} />}
                >
                  {
                    <Carousel
                      dots={false}
                      arrows={true}
                      prevArrow={<LeftCircleFilled />}
                      nextArrow={<RightCircleFilled />}
                    >
                      {item.images.map((image, index) => (
                        <div key={index}>
                          <Image src={image.url} width="100%" />
                        </div>
                      ))}
                    </Carousel>
                  }
                </Card>
              </List.Item>
            )}
          />
        </>
      );
    }
}
  

export default SearchStays;
