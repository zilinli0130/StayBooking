/**
 * Copyright (c) 2023
 *
 * @summary Implementation of GuestHomePage.js
 * @author Zilin Li
 * @date 2023-06-28
 *
 */

// React imports
import React from "react";

// Antd imports
import { Tabs } from "antd";

// Project imports
import MyReservations from "./MyReservations";
import SearchStays from "./SearchStays";

const { TabPane } = Tabs;

class GuestHomePage extends React.Component {
  render() {
    return (
      <Tabs defaultActiveKey="1" destroyInactiveTabPane={true}>
        <TabPane tab="Search Stays" key="1">
            <SearchStays></SearchStays>
        </TabPane>
        <TabPane tab="My Reservations" key="2">
          <MyReservations></MyReservations>
        </TabPane>
      </Tabs>
    );
  }
}

export default GuestHomePage;