const detailedUserData = [
  {
    username: 'userY',
    followers: ['userX', 'userC'],
    following: ['userD', 'userX'],
  },
  {
    username: 'userD',
    followers: ['userY', 'userZ', 'userA', 'userX', 'userB', 'userC'],
    following: ['userB', 'userY', 'userZ', 'userA'],
  },
  {
    username: 'userZ',
    followers: ['userD', 'userY', 'userX', 'userB'],
    following: ['userC', 'userX', 'userD', 'userB'],
  },
  {
    username: 'userA',
    followers: ['userB', 'userD'],
    following: ['userC', 'userX', 'userB'],
  },
  {
    username: 'userX',
    followers: ['userZ', 'userD', 'userY', 'userC', 'user1'],
    following: ['userZ', 'userA', 'userD', 'userY', 'userB', 'user1'],
  },
  {
    username: 'userB',
    followers: ['userC', 'userA', 'userD', 'userX', 'userZ'],
    following: ['userD', 'userX', 'userZ', 'userY'],
  },
  {
    username: 'userC',
    followers: ['userY', 'userB', 'userD', 'userX', 'userZ', 'userA', 'user1'],
    following: ['userA', 'userB', 'userD', 'user1'],
  },
  {
    username: 'user1',
    followers: ['userB', 'userX', 'userD'],
    following: ['userX', 'userC'],
  },
];

export default detailedUserData;
